package ceit.aut.ac.ir.utils;

import ceit.aut.ac.ir.model.Note;

import java.io.*;


public class FileUtils {

    private static final String NOTES_PATH = "./notes/";

    //It's a static initializer. It's executed when the class is loaded.
    //It's similar to constructor
    static {
        boolean isSuccessful = new File(NOTES_PATH).mkdirs();
        System.out.println("Creating " + NOTES_PATH + " directory is successful: " + isSuccessful);
    }

    public static File[] getFilesInDirectory() {
        return new File(NOTES_PATH).listFiles();
    }


    public static String fileReader(File file) throws  IOException {
        //TODO: Phase1: read content from file
        // done
        BufferedReader reader = new BufferedReader(new FileReader (file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
        } finally {
            reader.close();
        }
        return stringBuilder.toString();}

    public static void fileWriter(String content) {
        //TODO: write content on file
        //done
        String fileName = getProperFileName(content);

            File newTextFile = new File(NOTES_PATH,fileName+".txt");
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(newTextFile))) {

                bfw.write(content);

            } catch (IOException iox) {

                iox.printStackTrace();
            }
        }
        //TODO: Phase1: define method here for reading file with InputStream
        //done

    public static String  fileReaderInput(File file) throws IOException {
        FileInputStream fis=new FileInputStream(file);
        byte data[]=new byte[fis.available()];
        fis.read(data);
        fis.close();
        String str=new String(data);
        return str ;

    }

    //TODO: Phase1: define method here for writing file with OutputStream
    //done
    public static void fileWriterInput(String content){
        String fileName = getProperFileName(content);
        File file = new File(NOTES_PATH,fileName+".txt");
        try( FileOutputStream fos = new FileOutputStream(file)){
            byte[] bytesArray = content.getBytes();
            fos.write(bytesArray);
            fos.flush();
    }catch(IOException e) {
            e.printStackTrace();
        }
    }
    //TODO: Phase2: proper methods for handling serialization
    //done 

    public  static void writeSerialize(Note note){

        String fileName = getProperFileName(note.getTitle()+".txt");
        File file = new File(NOTES_PATH,fileName);
        try(FileOutputStream fs = new FileOutputStream(file)){
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(note);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readSerialize(File file) {
        String content = null ;
        try (FileInputStream fi = new FileInputStream(file)){
            ObjectInputStream  oi = new ObjectInputStream(fi);
            Note note = (Note)oi.readObject();
             content = note.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return content ;
    }

    private static String getProperFileName(String content) {
        int loc = content.indexOf("\n");
        if (loc != -1) {
            return content.substring(0, loc);
        }
        if (!content.isEmpty()) {
            return content;
        }
        return System.currentTimeMillis() + "_new file.txt";
    }
}
