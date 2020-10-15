package Dictionary;

import java.io.*;
import java.util.*;


public class DictionaryManagement extends Dictionary {

  public   Map<String,Boolean> yourWords = new LinkedHashMap<>();


    /**
     *  Lấy dữ liệu từ File ANH Việt
     * @param path đường dẫn file
     * @param ChangedWords  true -> lấy dữ liệu trong File ChangedWords
     */

    public void insertFromFile(String path, boolean ChangedWords)   {


        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       if( !scanner.hasNext())
           return;;

        String full_meaing ="" ;
        String short_meaning = "";
        String line = scanner.nextLine();
        String eng = "";
        String pronunc = "";

        if( !ChangedWords)
            full_meaing = line + '\n' + '\n';

        String[] str = line.split("\\@");

        for(int i=0; i<str.length ; i++)
             eng += str[i];


        while (scanner.hasNext()) {

            line = scanner.nextLine();


            if( !scanner.hasNext()) {
                full_meaing +=  line ;
                this.Put(eng,pronunc, full_meaing,short_meaning);
                yourWords.put(eng,false);
            }

           if( !line.isEmpty()) {

               if (line.startsWith("@", 0)) {

                   if (!dictionary.containsKey(eng)) {

                       this.Put(eng,pronunc,full_meaing,short_meaning);

                       yourWords.put(eng, false);
                   }

                   if (ChangedWords) {

                       this.Put(eng,pronunc,full_meaing,short_meaning);
                   }

                   full_meaing = "";


                   int index = 1;
                   for (int i = 1; i < line.length(); i++) {

                       if (line.charAt(i) == '/') {

                           index = i;

                           break;
                       }
                   }

                   String s2 = "";
                   if (index != 1 && line.charAt(line.length() - 1) == '/') {
                       eng = line.substring(1, index - 1);
                       pronunc = line.substring(index, line.length());

                   } else {
                       pronunc = "";

                       String[] s = line.split("\\@");

                       String ss = "";
                       for (int i = 0; i < s.length; i++)
                           ss += s[i];

                       eng = ss;
                   }


                   full_meaing += eng + "     " + pronunc + "\n" + "\n";

               } else if (line.startsWith("*", 0)) {

                   full_meaing += '\n' + line + "\n" + '\n';
               } else
                   full_meaing += line + "\n" + "\n";

           }


        }

    }



    public String Lookup(String input) {

        input = input.toLowerCase();

        if( dictionary.containsKey(input) )
            return this.getFullMeaning(input);

        return  "Can not define the word.";
    }


   // Lấy danh sách các từ gợi ý  khi tra
   public List<String>  dictionarySeacher(String input) {

       input = input.toLowerCase();
        List<String> list = new ArrayList<>();
       for (String key : this.dictionary.keySet()) {
            Word value = this.dictionary.get(key);


                if (key.startsWith(input, 0) && key.length() > input.length()) {
                    list.add(key);

                } else if( key.equals(input)) {

                    list.add(input);
                }
            }

     return list;
    }

    // Viết vào file
    public void WriteDataToFile(String word, String path) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {

            bw.write(word + '\n');
            // không cần đóng BufferedWriter (nó đã tự động đóng)
            // bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Xóa từ trong File
    public void  DeleteDataFromFile(String word, String path) {

        // Viet trc de lay du lieu
        List<String> list = GetDataFromFile(path);

        PrintWriter writer = null;
        try {
            // Xoa du lieu co trong file
            writer = new PrintWriter(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        list.remove(word);


        for(String s : list) {
            writer.append(s + '\n');         // Ghi du lieu vao file

        }

        writer.flush();

    }

    // Lấy dữ liệu từ file
     public List<String> GetDataFromFile(String path) {

        String input = "";
         List<String> list = new ArrayList<>();

         try {
            Scanner sc = new Scanner(new File(path));
             while (sc.hasNextLine()) {
                 input = sc.nextLine();
                 list.add(input);
             }
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

       return  list;
     }

     // Khởi tạo các từ thuộc phần Your Words
     public void InitYourWords() {

        List<String> list  = GetDataFromFile("File/YourWords.txt");

        for(String s : list) {
            yourWords.replace(s,true);
        }

     }

    /**
     *  Khởi tạo từ điển với các từ được thêm ,sửa xóa
     */

    public void InitNew_Changed_DeletedWords() {

        insertFromFile("File/NewWords.txt",false);
        insertFromFile("File/ChangedWords.txt",true);

         List<String> list = GetDataFromFile("File/DeletedWords.txt");

         for(String s : list)
             dictionary.remove(s);


     }

    /**
     *  Lấy danh sách các từ thuộc Your Words
     */

    public List<String> ListYourWord() {

        List<String> list = new ArrayList<>();

        for (Map.Entry<String, Boolean> word : yourWords.entrySet()) {

            String Eng_word = word.getKey();

            if( word.getValue() == true)
                list.add(Eng_word);

        }

        return list;
    }

    /**
     *
     * @param word từ cần so sánh - thuộc từ điển
     * @param key  từ khóa - từ khi gõ sai
     * @return true -> word gần đúng với key
     */

    public boolean CompareWords(String word, String key)
    {

        List<String> list = new ArrayList<>();

        int saiso = (int)Math.round(key.length() * 0.3);

        if(word.length() < (key.length() - saiso) | word.length() > (key.length() + saiso) )
            return false;

        int loi = 0, i = 0, j = 0;

        do {
            if (key.charAt(i) != word.charAt(j)) {
                loi++;
                for (int l = 1; l < saiso; l++) {
                    if (i + l < key.length()) {
                        if (key.charAt(i + l) == word.charAt(j)) {
                            i += l;
                            break;
                        }
                    } else if (j + l < word.length()) {
                        if (word.charAt(j + l) == key.charAt(i)) {
                            j += l;
                            break;
                        }
                    }
                }
            }
            i++;
            j++;
        } while (!(i >= key.length() | j >= word.length()));
        loi+= key.length() - j + word.length() - j;
        return loi <= saiso;
    }

    /**
     * Trả về danh sách từ phỏng doán gần đúng với từ khi gõ sai
     */

   public List<String> ListOfGuessCorrectWords(String key) {

      List<String> list = new ArrayList<>();
      for(String word: this.dictionary.keySet()) {

          if( CompareWords(key,word))
              list.add(word);

      }

     return list;
   }

   public void InitDictionary() {

        insertFromFile("File/AnhViet.txt",false);
        InitNew_Changed_DeletedWords();

        InitYourWords();
   }

    public static void main(String[] args) {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.InitDictionary();



    }

}
