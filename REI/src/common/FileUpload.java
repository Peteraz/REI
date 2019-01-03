package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.struts2.ServletActionContext;



public class FileUpload{
	
	//上传文件存放路径   
    private final static String UPLOADDIR = "images";   
   
    //上传文件集合   
    private List<File> file;   
    //上传文件名集合   
    private List<String> fileFileName;    
    

    
    public List<String> upload()throws FileNotFoundException, IOException{
    	 System.out.println(this.getFileFileName()); 
    	 
    	 List<String> ls=new ArrayList();
         for (int i = 0; i < file.size(); i++) {   
             //循环上传每个文件   
             ls.add(uploadFile(i));   
         }  
         
         return ls;
    }
    
    
    
    //执行上传功能   
    private String uploadFile(int i) throws FileNotFoundException, IOException {   
        String filepath="";
    	try {   
            InputStream in = new FileInputStream(file.get(i));                        //文件输入流
            String dir = ServletActionContext.getRequest().getRealPath(UPLOADDIR);    //获取ServletAction上下文对象，getServletContext()获取Servlet上下文对象，getRealPath("upload")获取upload的绝对路径。
            System.out.println(dir);
            File fileLocation = new File(dir);  
            //此处也可以在应用根目录手动建立目标上传目录  
            if(!fileLocation.exists()){                           //exists()检查文件是否存在，f.exists()返回false，即存在，!f.exists()返回true，即不存在
                boolean isCreated  = fileLocation.mkdir();        //不存在，使用mkdir()方法创建一个
                if(!isCreated) {  
                    //目标上传目录创建失败,可做其他处理,例如抛出自定义异常等,一般应该不会出现这种情况。  
                    return "文件夹创建失败";  
                }  
            }  
            String fileName=this.getFileFileName().get(i);  
            
            String newfileName=getRandomFileName(fileName);
            
            File uploadFile = new File(dir, newfileName);           //有两个参数，第一个是路径，第二个是文件名   
            
            OutputStream out = new FileOutputStream(uploadFile);    //文件输出流
            byte[] buffer = new byte[1024 * 1024];              //1MB
            int length;   
            while ((length = in.read(buffer)) > 0) {   
                out.write(buffer, 0, length);   
            }   
            in.close();   
            out.close();
            filepath=UPLOADDIR+"/"+newfileName;                     //上传文件的路径
        } catch (FileNotFoundException ex) {   
            System.out.println("上传失败!");  
            ex.printStackTrace();   
        } catch (IOException ex) {   
            System.out.println("上传失败!");  
            ex.printStackTrace();   
        }   
        return  filepath;
        
    }   
    
    
    //
    private  String getRandomFileName(String sfilename) {  
    	  
        SimpleDateFormat simpleDateFormat;  
  
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");  
  
        Date date = new Date();  
  
        String str = simpleDateFormat.format(date);  
  
        Random random = new Random();  
  
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
  
        String ext=sfilename.substring(sfilename.lastIndexOf("."),sfilename.length());

        StringBuffer sb=new StringBuffer();
        
        
        String filename=sb.append(rannum).append(str).append(ext).toString();

       return filename; 
    }  
    
    
    public List<File> getFile() {   
        return file;   
    }   

    public void setFile(List<File> file) {   
        this.file = file;   
    }   

   public List<String> getFileFileName() {   
       return fileFileName;   
   }   

    public void setFileFileName(List<String> fileFileName) {   
        this.fileFileName = fileFileName;   
    }   

    
    
    
    
    
}
