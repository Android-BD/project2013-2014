package VJoy;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

public class VJoyDriver {
	public interface VJoy32 extends Library {
	    VJoy32 INSTANCE = (VJoy32)Native.loadLibrary("VJoy32", VJoy32.class);
	    boolean VJoy_Initialize(String name,String serial);
	    void VJoy_Shutdown();
	    boolean VJoy_UpdateJoyState(int id, JOYSTICK_STATE.ByReference pJoyState);
	    
		public static class JOYSTICK_STATE extends Structure {
			public static class ByReference extends JOYSTICK_STATE implements Structure.ByReference {}
	    	public byte ReportId; //unsigned
	    	public short XAxis;
	    	public short YAxis;
	    	public short ZAxis;
	    	public short XRotation;
	    	public short YRotation;
	    	public short ZRotation;
	    	public short Slider;
	    	public short Dial;
	    	public short Wheel;
	    	public short POV; //unsigned
	    	public int Buttons; //unsigned
	    	
	        @Override
	        protected List<String> getFieldOrder() {
	            return Arrays.asList(new String[] {
	                    "ReportId", "XAxis", "YAxis", "ZAxis", "XRotation", "YRotation", "ZRotation", "Slider", "Dial", "Wheel", "POV", "Buttons"  });
	        }
		}
					
	}	
	
	//deklaracja potrzebnych zmiennych
	VJoy32.JOYSTICK_STATE.ByReference m_joyState;
	VJoy32 vDLL;
	
	//tablica zawieraj�ca warto�ci numeryczne poszczeg�lnych przycisk�w, 2^(7+i)
	short[] buttonID;
	//tablica przechowywuj�ca informacje o tym kt�re przyciski s� aktualnie wci�ni�te, true/false
	boolean[] buttonPressed;
	
	//osie dw�ch galek analogowych
	short axisX1,axisY1,axisX2,axisY2;
	
	//klasa z w�tkiem reinicjalizuj�cym sterownik co 5 minut
	VJoyReinit reinit;
	
	public VJoyDriver(boolean autoInit)
	{
		//autoInit - je�li true to automatycznie inicjalizujemy sterownik i uruchamiamy w�tek reinicjalizuj�cy
		//ver - 32 albo 64, kt�rej wersji sterownika u�y�
		
		//NA TEMAT ZMIENNYCH BO SA MA�O LOGICZNE:
		//Wheel zawiera przyciski 1-8 ze wzorem 2^(7+i) oraz prawdopodobnie pomi�dzy nimi kombinacje dla d�wigni POV (z regu�y to d-pad)
		//POV zawiera przyciski 9-24
		//Dial obs�uguje Tarcz�, czymkolwiek ona jest, oraz zielony POV
		//Slider = suwak
		//Buttons nie mam bladego poj�cia co zawiera bo nic nie robi
		//Axis i Rotation dzia�aj� zgodnie z przewidywaniami, poza ZAxis - z jakiego� powodu nie widze jej wp�ywu na o� Z, sprawdzi sie
		//zakresy osi w SDK podane s� od -32768 do 32767 ale nie mam poj�cia czemu a� taka skoro i tak si� zap�tla. Dlatego
		//przyjmujemy dok�adno�� -127 do 127.
		
		//dla standardowego pada w zupe�no�ci powinno nam starcze� 10-12 przycisk�w, standardowe osie X, Y, Z oraz POV 
		buttonID=new short[33];
		buttonPressed=new boolean[33];

		for(int i=0;i<=32;i++)
		{
			buttonID[i]=(short)Math.pow(2,7+i);
			buttonPressed[i]=false;
		}
		
		reinit=new VJoyReinit(this);
		
		if(autoInit)
		{
			VJoyStart();			
		}
		
	}
	
	public void VJoyStart()
	{
		VJoyInit();
		new Thread(reinit).start();
	}
	
	public void VJoyInit()
	{
		String myLibraryPath = System.getProperty("user.dir")+"\\dll\\";
		System.setProperty("jna.library.path", myLibraryPath);
		
		System.out.printf(myLibraryPath+"\n");
		
		
		vDLL=VJoy32.INSTANCE;
		
		boolean result=vDLL.VJoy_Initialize("","");
		if(result==true)		
		System.out.printf("Inicjalizacja zakonczona powodzeniem");
		else
		System.out.printf("Inicjalizacja nieudana");
		
		m_joyState= new VJoy32.JOYSTICK_STATE.ByReference();
		
		vDLL.VJoy_UpdateJoyState(0, m_joyState);
	}
	
	public boolean buttonPress(int buttonNumber)
	{
		//wciskamy przycisk o podanym numerze
		buttonPressed[buttonNumber]=true;
		if(updateButtons())		
		return true;
		else return false;
	}
	
	public boolean buttonRelease(int buttonNumber)
	{
		//puszczamy przycisk o podanym numerze
		buttonPressed[buttonNumber]=false;
		if(updateButtons())		
		return true;
		else return false;
	}
	
	public boolean updateButtons()
	{
		//aktualizujemy stan przycisk�w
		short valueWheel=0;
		short valuePOV=0;
		
		for(int i=1;i<=8;i++)
		{
			if(buttonPressed[i])
				valueWheel+=buttonID[i];
		}
		
		for(int i=9;i<=24;i++)
		{
			if(buttonPressed[i])
				valuePOV+=(short)Math.pow(2,i-9);
		}
		
		for(int i=25;i<=28;i++)
		{
			if(buttonPressed[i])
			{
				valueWheel+=16*(i-25);
				break;
			}
		}
		
		m_joyState.Wheel=valueWheel;
		m_joyState.POV=valuePOV;
		
		if(vDLL.VJoy_UpdateJoyState(0,m_joyState))
		return true;
		else return false;
	}
	
	public boolean updateAxes(int axisNr,int cordX,int cordY)
	{
		//aktualizujemy stan ga�ki axisNr (1 lub 2) i ustawiamy j� na [cordX,cordY], ka�da o� w zakresie [-127,127]
		if(axisNr==1)
		{
			m_joyState.XAxis=(short)cordX;
			m_joyState.YAxis=(short)cordY;
		}
		else
		{
			m_joyState.ZAxis=(short)cordX;
			m_joyState.ZRotation=(short)cordY;
		}
			
		if(vDLL.VJoy_UpdateJoyState(0,m_joyState))
		return true;
		else return false;
	}
	
	public void close()
	{
		vDLL.VJoy_Shutdown();
	}
	
	public boolean testButtons(short i)
	{

			m_joyState.Wheel=i;
		
		if(vDLL.VJoy_UpdateJoyState(0,m_joyState))
		return true;
		else return false;
	}

}
