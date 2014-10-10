package helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldHelper {

    /**
     * Set the field value of a bean.
     * @param root
     * @param bean
     * @param name
     * @param value
     */
    public static void setBeanField(Class<?> root, Object bean, String name, Object value){
        try {
            setField( root, bean.getClass(), bean, name, value );
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    private static void setField(Class<?> root, Class<?> clazz, Object obj, String fieldName, Object value)
        throws Exception
    {
    	Method set = null;
    	Field field = null;
        try{
            field = clazz.getDeclaredField(fieldName);
            set = clazz.getDeclaredMethod("set" + StringHelper.capitalize(fieldName), field.getType());
            set.invoke(obj, value);
        }

        catch(NoSuchFieldException e){
            if( ! root.getName().equals(clazz.getName()) ){
                setField( root, clazz.getSuperclass(), obj, fieldName, value );
            }else{
                //e.printStackTrace();
            }
        }
    }

    /**
     * Get the fields of a certain bean.
     * @param thic
     * @param root
     * @return
     */
    public static Field[] getBeanFields( Class<?> thic, Class<?> root ){
        Class<?> supc = thic.getSuperclass();

        List<Field> declared = Arrays.asList( thic.getDeclaredFields() );
        List<Field> inherated = Arrays.asList( getInheratedFields( supc, root ) );

        List<Field> rslt = new ArrayList<Field>();

        for( Field d : declared ){
            if( d.getName().startsWith("_") && d.getName().toLowerCase().equals(d.getName())){
                rslt.add(d);
            }
        }

        for( Field i : inherated ){
            if( i.getName().startsWith("_") && i.getName().toLowerCase().equals(i.getName())){
                rslt.add(i);
            }
        }

        Field[] fields = new Field[rslt.size()];

        for( int i = 0; i < rslt.size(); i++ ){
            fields[i] = rslt.get(i);
        }

        return fields;
    }

    private static Field[] getInheratedFields( Class<?> supc, Class<?> root ) {

        if( root.getName().equals(supc.getName()) ){

            return supc.getDeclaredFields();

        }else{

            return getBeanFields(supc, root);

        }
    }
}
