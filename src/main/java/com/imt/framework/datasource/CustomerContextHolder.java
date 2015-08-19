package com.imt.framework.datasource;

public class CustomerContextHolder {
    public final static String DATA_SOURCE_DS1 = "ds1";
    public final static String DATA_SOURCE_DS2 = "ds2";
    
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    
    public static void setCustomerType(String customerType) {  
        contextHolder.set(customerType);  
    }  
      
    public static String getCustomerType() {  
        return contextHolder.get();  
    }  
      
    public static void clearCustomerType() {  
        contextHolder.remove();  
    }  
}
