package cn.com.shopec.core.themepavilion.common;
/**
 * 主题馆枚举设置
 * @author LiHuan
 * Date 2017年10月31日 下午5:31:02
 */
public enum ThemePavilionEnum {

	//星期枚举应用
	MON(1,"周一"), TUE(2,"周二"), WED(3,"周三"), THU(4,"周四"),FRI(5,"周五"),SAT(6,"周六"),SUN(7,"周日");
	
	int index;
	String name;
	
	ThemePavilionEnum(int index,String name) {  
	        this.index = index;  
	        this.name = name;
	    }  
	      
		// 普通方法
	    public static String getName(int index) {
	      for (ThemePavilionEnum c : ThemePavilionEnum .values()) {
	        if (c.getIndex() == index) {
	          return c.name;
	        }
	      }
	      return null;
	    }
	    public int getIndex() {
	        return index;
	    }
	   
	    public void setIndex(int index) {
	        this.index = index;
	    }
	    
	    public String getName() {  
	        return name;  
	    }  
}
