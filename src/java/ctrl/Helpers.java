/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

/**
 *
 * @author hmoraga
 */
public class Helpers {
    /**
     * Me transforma un entero (cantidad de segundos) en su version de texto,
     * en formato hh:mm:ss.
     * @param aInt cantidad de segundos a ser convertida en formato de hora
     * @return la cantidad de segundos ingresada en formato hh:mm:ss
     */
    public static String getSecondsToHourFormat(int aInt) {
        String s = "";
        int res = aInt;
        int hh = res/3600;
        res -= hh*3600;
        int mm = res/60;
        int ss = res % 60;
        
        if (hh!=0){            
            s = s + ((hh<10)?"0"+hh:hh) + ":";
        }
            
        if (mm!=0){
            s = s + ((mm<10)?"0"+mm:mm) + ":";
        }
            
        s = s + ((ss<10)?"0"+ss:ss);
        
        return s;
    }
    
    /**
     * Me transforma un texto de horas formato hh:mm:ss, a su version cantidad
     * de segundos (como entero).
     *
     * @param timeString texto con la hora a convertir
     * @return la cantidad de segundos convertida
     */
    public static int getHourFormatToSeconds(String timeString) {
        String[] aux = timeString.split(":");
        
        switch (aux.length) {
            case 3:
                return Integer.parseInt(aux[0])*3600+Integer.parseInt(aux[1])*60+Integer.parseInt(aux[2]);
            case 2:
                return Integer.parseInt(aux[0])*60+Integer.parseInt(aux[1]);
            case 1:
                return Integer.parseInt(aux[0]);
            default:
                return 0;
        }
    }
}
