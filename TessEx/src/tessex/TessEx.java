/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tessex;
import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import net.sourceforge.tess4j.*;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
/**
 *
 * @author ANKIT
 */
public class TessEx {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Integer> x,y,roots;
    static int a1=0,b1=0,c1=0,d1=0;
    
   public void process(String path) throws Exception
   {
       
       String s=null;
        int flag=68;
        Integer a=0,b=0,d=0,e=0;
        double root1=0,root2=0;
        File imageFile = new File(path);
        ITesseract instance = new Tesseract(); 
        instance.setDatapath("C:\\Users\\ANKIT\\Downloads\\Tess4J\\tessdata"); 
        instance.setLanguage("eng");// JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping

        
        try {
            String result = instance.doOCR(imageFile);
            s=result;
        } catch (TesseractException err) {
            System.out.println(err.getMessage());
        }
        if(s.length()<20){
          char c[] = s.toCharArray();
          for(int i=0;i<s.length();i++)
          {
              System.out.print(c[i]+" ");
          }
        for(int i=0;i<s.length();i++)
        {
             if(c[i]=='x'||c[i]=='X')
            {
                if(c[i+1]=='3')
                {
                    flag=2;
                   break;
                }
                else if(c[i+1]=='2')
                {
                    flag=0;
                   break;
                }
                else 
                    {
                    flag=1;
                    continue;
                    }
            }
        }
        if(flag==1)
        {
              String s1[] = s.split("\\+");
            System.out.println("Linear Equation");
            for(int i=0;i<s1.length;i++)
            {
           if(s1[i].matches("(.*)x"))
                {
                    a=Integer.parseInt(s1[i].substring(0, s1[i].indexOf("x")));
                }
            else  if(s1[i].trim().matches("[0-9]+"))
                {
                   b=Integer.parseInt(s1[i].trim());
                }
            
            }
            System.out.println(" a = "+a+" b = "+b);
        double coeff [] = {b,a};
        new FunctionOfYOverScatter(coeff);
      
        }
        else if(flag==0)
        {
            String s1[] = s.split("\\+");
            for(int i=0;i<s1.length;i++)
            {
                
                if(s1[i].matches("(.*)x2(.*)"))
                {
                    a=Integer.parseInt(s1[i].substring(0, s1[i].indexOf("x")));
                }
                else if(s1[i].matches("(.*)x"))
                {
                    b=Integer.parseInt(s1[i].substring(0, s1[i].indexOf("x")));
                }
                if(s1[i].trim().matches("[0-9]+"))
                {
                   e=Integer.parseInt(s1[i].trim());
                }
            }
            
             d = b * b - 4 * a * e;
        if(d > 0)
        {
            System.out.println("Roots are real and unequal");
            root1 = ( - b + Math.sqrt(d))/(2*a);
            root2 = (-b - Math.sqrt(d))/(2*a);
        }
        else if(d == 0)
        {
            System.out.println("Roots are real and equal");
            root1 = (-b+Math.sqrt(d))/(2*a);
            System.out.println("Root:"+root1);
        }
        else if(d<0)
        {
            System.out.println("Roots are imaginary");
        }
        System.out.println("First Root = "+root1);
        System.out.println("Second Root = "+root2);
        double coeff [] = {e,b,a};
        new FunctionOfYOverScatter(coeff);
        } 
        else{
            //Cubic ADDed by MOHIT
            ArrayList<Integer> factors=new ArrayList<>();
		
		System.out.println("Cubic Equations");
		String arr[]=s.split("\\+");
		if(arr[0].substring(0, arr[0].indexOf("x")).length()==0)
			a1=1;
		else 
		 a1=Integer.parseInt(arr[0].substring(0, arr[0].indexOf("x")));
		
		if(arr[1].substring(0, arr[1].indexOf("x")).length()==0)
			 b1=1;
		else
			 b1=Integer.parseInt(arr[1].substring(0, arr[1].indexOf("x")));
		 
		if(arr[2].substring(0, arr[1].indexOf("x")).length()==0)
			   c1=1;
			else
		       c1=Integer.parseInt(arr[2].substring(0, arr[2].indexOf("x")));
		
		 d1=Integer.parseInt(arr[3].trim());
		System.out.println(a1+" "+b1+" "+c1+" "+d1);
                double coeff[] = {d1,c1,b1,a1};
                new FunctionOfYOverScatter(coeff);
                
		for(int i=1;i<=d1;i++)
		{
			if(d1%i==0){
				factors.add(i);
				factors.add(-i);
			}
		}
		for(int i=0;i<factors.size();i++)
		{
			int x=factors.get(i);
			int y=a1*x*x*x+b1*x*x+c1*x+d1;
			if(y==0){
				System.out.println("value :"+x);
			}}
		
		 x=new ArrayList<>();
		 y=new ArrayList<>();
		
		
		for(int i=0;i<10;i++)
		{
			int temp1=i;
			x.add(i);
			int temp=a1*i*i*i+b1*i*i+c1*i+d1;
			y.add(temp);
		}
		for(int i=0;i<10;i++)
		{
			System.out.println(x.get(i)+" "+y.get(i));
		}
                
                

        }
        
    }else{
        ArrayList<String> nnp=new ArrayList<>();
      ArrayList<Integer> cd=new ArrayList<>();
      //Loading the Tokenizer model 
      InputStream inputStream = new FileInputStream("./src/resources/en-token.bin"); 
      
      InputStream inputStream2 = new 
    	         FileInputStream("./src/resources/en-pos-maxent.bin"); 
    	      POSModel model = new POSModel(inputStream2); 
    	       
    	      //Creating an object of WhitespaceTokenizer class  
    	      WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE; 
    	      
    	      //Tokenizing the sentence 
    	      String[] tokens12 = whitespaceTokenizer.tokenize(s); 
    	        
    	      boolean a2=false,sub=false;
    	      String addition[]={"add","sum","total","all together","both","combined","in all","increase by"};
    	      String subtraction[]={"Subtraction","decreased by","difference","fewer than","left","less","less than","minus","remaining","take away"};
    	          	      
    	      for(String add:addition)
    	      {
    	    	  if(s.contains(add)){
    	    		  System.out.println(add);
    	    		 a2=true;
    	    	  }
    	      }
    	      
    	      //Instantiating POSTaggerME class 
    	      POSTaggerME tagger = new POSTaggerME(model); 
    	       
    	      //Generating tags 
    	      String[] tags = tagger.tag(tokens12); 
    	     
    	       
    	      //Instantiating POSSample class       
    	      POSSample sample = new POSSample(tokens12, tags); 
    	      
    	      System.out.println(sample); 
    	      String tagg[]=sample.getTags();
    	      String sen[]=sample.getSentence();
              
    	      for(int i=0;i<tagg.length;i++){
    	    	 
    	    	if(tagg[i].equals("NNP")){
    	    	//	System.out.println(tagg[i]+" "+sen[i]);
    	    		nnp.add(sen[i]);
    	    	}
    	    	else if(tagg[i].equals("CD")){
    	    		//System.out.println(tagg[i]+" "+sen[i]);	
    	    		cd.add(Integer.parseInt(sen[i]));
    	    	}
    	    	System.out.println(tagg[i]+" "+sen[i]);	
    	      }
    	       if(sample.getTags().equals("NNP")){
    	    	   System.out.println("NNP =>"+sample);
    	       }
    	      //Monitoring the performance of POS tagger 
    	      
    	      for(int j=0;j<nnp.size();j++){
    	    	  System.out.println(nnp.get(j)+" "+cd.get(j));
    	      }
    	      InputStream inputStream3 = new 
    	    	         FileInputStream("./src/resources/en-ner-person.bin"); 
    	    	      TokenNameFinderModel model1 = new TokenNameFinderModel(inputStream3);
    	    	      
    	    	      //Instantiating the NameFinder class 
    	    	      NameFinderME nameFinder = new NameFinderME(model1); 
    	    	 
    	    	     
    	    	    	  if(a2)
    	    	    	  System.out.println(cd.get(0)+cd.get(1));
    	    	    	  else
    	    	    		  System.out.println(cd.get(1)-cd.get(0));
    	    	      
    	    	      //Printing the spans of the names in the sentence 
  
        
        }
   
   
   
   } 
}

