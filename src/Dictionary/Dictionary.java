package Dictionary;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

   public Map<String, Word> dictionary = new HashMap<>();

   public void  Put(String key,String pronunc, String full_meaning, String short_meaning) {

       Word word = new Word(pronunc,full_meaning,short_meaning);
       dictionary.put(key,word);
   }

   public void setPronunc(String key, String pronunc)  {

       dictionary.get(key).pronunc = pronunc;

   }

    public String getPronunc(String key)  {


       return dictionary.get(key).pronunc;
    }

    public void setFullMeaning(String key, String meaning) {
        dictionary.get(key).full_meaning = meaning;
    }

    public void setShortMeaning(String key, String meaning) {
        dictionary.get(key).short_meaning = meaning;
    }

    public String getFullMeaning(String key) {
       return dictionary.get(key).full_meaning;
    }

    public String getShortMeaning(String key) {
        return dictionary.get(key).short_meaning;
    }


}
