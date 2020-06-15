import java.util.Arrays;
import java.util.Vector;
import javax.print.attribute.IntegerSyntax;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class Task_6 {
    
    //1
    //Число Белла - это количество способов, которыми массив из n элементов может
    //быть разбит на непустые подмножества. Создайте функцию, которая принимает
    //число n и возвращает соответствующее число Белла.

    private static int C(int n, int k)
    {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    private static int factorial(int n)
    {
        if (n == 0) return 1;
        else
        {
            int result = 1;
            for(int i = 2; i <= n; i++)
            {
                result *= i;
            }
            return result;
        }
    }

    public static int Bell(int n)
    {
        if (n == 0) return 1;
        else
        {
            int sum = 0;
            for (int k = 0; k < n; k++)
            {
                sum += C(n-1, k) * Bell(n - k - 1);
            }
            return sum;
        }
    }
    
    //2
    //. В «поросячей латыни» (свинский латинский) есть два очень простых правила:
    //– Если слово начинается с согласного, переместите первую букву (буквы) слова до
    //гласного до конца слова и добавьте «ay» в конец.
    //have ➞ avehay
    //cram ➞ amcray
    //take ➞ aketay
    //cat ➞ atcay
    //shrimp ➞ impshray
    //trebuchet ➞ ebuchettray
    //– Если слово начинается с гласной, добавьте "yay" в конце слова.
    //ate ➞ ateyay
    //apple ➞ appleyay
    //oaken ➞ oakenyay
    //eagle ➞ eagleyay
    //Напишите две функции, чтобы сделать переводчик с английского на свинский латинский.
    //Первая функция translateWord (word) получает слово на английском и возвращает это
    //слово, переведенное на латинский язык. Вторая функция translateSentence (предложение)
    //берет английское предложение и возвращает это предложение, переведенное на латинский язык
    //Примечание:
    //– Регулярные выражения помогут вам не исказить пунктуацию в предложении.
    //– Если исходное слово или предложение начинается с заглавной буквы, перевод должен
    //сохранить свой регистр

    private static int firstVowel(String word)
    {
        String vowels = "AaEeIiOoUuYy";
        for (int i = 0; i < word.length(); i++)
        {
            if (vowels.contains(Character.toString(word.charAt(i))))
            {
                return i;
            }
        }
        return -1;
    }

    public static String translateWord(String word)
    {
        if (word == "") return "";
        int firstVowel = firstVowel(word);
        if (firstVowel == 0)  return word + "yay";
        if (firstVowel == -1) return word + "ay";

        return word.substring(firstVowel) + word.substring(0, firstVowel) + "ay";
    }
    public static String translateSentence(String sentence)
    {
        String[] words = sentence.split(" ");
        String result  = "";
        for (int i = 0; i < words.length; i++)
        {
            String buffer = "";
            if (!Character.isLetter(words[i].charAt(words[i].length() - 1)))
            {
                char last = words[i].charAt(words[i].length() - 1);
                words[i] = words[i].substring(0, words[i].length() - 1);
                buffer = translateWord(words[i]) + Character.toString(last) + " ";
            }
            else
            {
                buffer = translateWord(words[i]) + " ";
            }

            if (buffer != buffer.toLowerCase())
            {
                buffer = buffer.substring(0, 1).toUpperCase() + buffer.substring(1).toLowerCase();
            }
            result += buffer;
        }
        return result;
    }


    //3
    //Учитывая параметры RGB (A) CSS, определите, является ли формат принимаемых
    //значений допустимым или нет. Создайте функцию, которая принимает строку
    //(например, " rgb(0, 0, 0)") и возвращает true, если ее формат правильный, в
    //противном случае возвращает false.

    public static boolean validColor(String color)
    {
        if (!color.contains("rgb"))
            return false;
        String value = color.substring(color.indexOf("(") + 1, color.indexOf(")"));
        String[] numbers = value.split(",");

        if (color.contains("rgba") && numbers.length != 4)
            return false;
        if (color.contains("rgb") && !color.contains("rgba") && numbers.length != 3)
            return false;

        for (int i = 0; i < 3; i++)
        {
            if (Integer.parseInt(numbers[i]) < 0 || Integer.parseInt(numbers[i]) > 255)
                return false;
        }

        if (numbers.length == 4)
        {
            if (Double.parseDouble(numbers[3]) < 0 || Double.parseDouble(numbers[3]) > 1)
                return false;
        }
        return true;

    }


    //4
    //Создайте функцию, которая принимает URL (строку), удаляет дублирующиеся
    //параметры запроса и параметры, указанные во втором аргументе (который будет
    //необязательным массивом).

    public static String stripUrlParams(String url, String ...param)
    {
        Map<String, Integer> values = new HashMap<String, Integer>();
        String[] parts = url.split("\\?");

        if (parts.length < 2)
            return url;

        String[] params = parts[1].split("&");

        for (String parameter : params)
        {
            String key = parameter.substring(0, parameter.indexOf('='));
            String valueStr = parameter.substring(parameter.indexOf('=') + 1);
            int value = Integer.parseInt(valueStr);
            values.put(key, value);

        }

        String result = "";

        result += parts[0] + "?";

        for (String key : values.keySet())
        {
            boolean notDisplay = false;
            for (int i = 0; i < param.length; i++)
            {
                if (param[i].contains(key))
                {
                    notDisplay = true;
                }
            }
            if (notDisplay)
                continue;
            result += key + "=" + values.get(key) + "&";
        }

        return result.substring(0, result.length() - 1);

    }
    
    //5
    //Напишите функцию, которая извлекает три самых длинных слова из заголовка
    //газеты и преобразует их в хэштеги. Если несколько слов одинаковой длины,
    //найдите слово, которое встречается первым.

    public static String[] getHashTags(String str)
    {
        String[] values = { "", "", ""};

        String[] words = str.split(" ");

        for(String word : words)
        {
            if (!Character.isLetter(word.charAt(word.length() - 1)))
                word = word.substring(0, word.length() - 1);

            if (word.length() > values[0].length())
            {
                values[2] = values[1];
                values[1] = values[0];
                values[0] = word;
            }
            else if (word.length() > values[1].length())
            {
                values[2] = values[1];
                values[1] = word;
            }
            else if (word.length() > values[2].length())
            {
                values[2] = word;
            }
        }

        for (int i = 0; i < values.length; i++)
        {
            values[i] = "#" + values[i];
        }

        return values;
    }
    
    //6
    //Создайте функцию, которая принимает число n и возвращает n-е число в
    //последовательности Улама.

    public static int ulam(int n)
    {
        Vector<Integer> values = new Vector<Integer>();

        values.add(1);
        values.add(2);

        for (int i = 3; i < 2000; i++)
        {
            int count = 0;
            for (int j = 0; j < values.size() - 1; j++)
            {
                for (int k = j + 1; k < values.size(); k++)
                {
                    if (values.get(j) + values.get(k) == i)
                        count++;

                }
            }
            if (count == 1)
                values.add(i);
            if (values.size() == n)
                return i;
        }

        return -1;
    }
    
    //7
    //Напишите функцию, которая возвращает самую длинную неповторяющуюся
    //подстроку для строкового ввода.
    //Примечание:
    //– Если несколько подстрок связаны по длине, верните ту, которая возникает первой.
    //– Бонус: можете ли вы решить эту проблему в линейном времени?

    public static String longestNonrepeatingSubstring(String str)
    {
        String result = "";
        String buffer = "";

        for (int i = 0; i < str.length(); i++)
        {
            if (!buffer.contains(str.substring(i, i + 1)))
            {
                buffer += str.substring(i, i + 1);
            }
            else
            {
                if (buffer.length() > result.length())
                    result = buffer;

                buffer = buffer.substring(buffer.indexOf(str.charAt(i)));
            }
        }
        if (buffer.length() > result.length())
            result = buffer;

        return result;
    }
    
    
    //8
    //Создайте функцию, которая принимает арабское число и преобразует его в римское
    //число.
    //Примечание:
    //– Все римские цифры должны быть возвращены в верхнем регистре.
    //– Самое большое число, которое может быть представлено в этой нотации, - 3,999.


    public static String convertToRoman(int num) {
        String res = "";
        while (num >= 1000) {
            res += "M";
            num -= 1000; }
        while (num >= 900) {
            res += "CM";
            num -= 900;
        }
        while (num >= 500) {
            res += "D";
            num -= 500;
        }
        while (num >= 400) {
            res += "CD";
            num -= 400;
        }
        while (num >= 100) {
            res += "C";
            num -= 100;
        }
        while (num >= 90) {
            res += "XC";
            num -= 90;
        }
        while (num >= 50) {
            res += "L";
            num -= 50;
        }
        while (num >= 40) {
            res += "XL";
            num -= 40;
        }
        while (num >= 10) {
            res += "X";
            num -= 10;
        }
        while (num >= 9) {
            res += "IX";
            num -= 9;
        }
        while (num >= 5) {
            res += "V";
            num -= 5;
        }
        while (num >= 4) {
            res += "IV";
            num -= 4;
        }
        while (num >= 1) {
            res += "I";
            num -= 1;
        }
        return res;
    }
    
    
    
        //9
        //Создайте функцию, которая принимает строку и возвращает true или false в
        //зависимости от того, является ли формула правильной или нет.

    public static boolean formula(String str) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String[] expressions = str.split("=");
        Vector<Integer> results = new Vector<Integer>();

        for (String expression : expressions)
        {
            results.add((Integer) engine.eval(expression));
        }

        int check = results.get(0);

        for (Integer value : results)
        {
            if (value != check)
                return false;
        }

        return true;

    }

    //10
    //Число может не быть палиндромом, но его потомком может быть. Прямой потомок
    //числа создается путем суммирования каждой пары соседних цифр, чтобы создать
    //цифры следующего числа.
    //Например, 123312 – это не палиндром, а его следующий потомок 363, где: 3 = 1 + 2; 6 = 3
    //+ 3; 3 = 1 + 2.
    //Создайте функцию, которая возвращает значение true, если само число является
    //палиндромом или любой из его потомков вплоть до 2 цифр (однозначное число -
    //тривиально палиндром).

    public static boolean palindromeDescendant(int num)
    {
        String number = Integer.toString(num);
        StringBuilder input = new StringBuilder();
        input.append(number);

        while (input.length() > 1)
        {
            if (input.toString().equals(input.reverse().toString()))
                return true;

            if (input.length() % 2 != 0)
                return false;

            String next = sumDigits(input.reverse().toString());

            input.setLength(0);
            input.append(next);
        }

        return false;
    }

    private static String sumDigits(String num)
    {
        String result = "";
        for (int i = 0; i < num.length() - 1; i+=2)
        {
            int sum = Character.digit(num.charAt(i), 10) + Character.digit(num.charAt(i + 1), 10);
            result += Integer.toString(sum);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("№1: " + Bell(10));
        System.out.println("№2: " + translateWord("button"));
        System.out.println("№2: " + translateSentence("Do you think, it is going to rain today?"));
        System.out.println("№3: " + validColor("rgb(255,255,255,0.2342)"));
        System.out.println("№4: " + stripUrlParams("https://edabit.com?a=1&b=2&a=2", new String[] {"b", "a"}));
        System.out.println("№5: " + getHashTags("Hey Parents, Surprise, Fruit Juice Is Not Fruit")));
        System.out.println("№6: " + ulam(206));
        System.out.println("№7: " + longestNonrepeatingSubstring("abcde"));
        System.out.println("№8: " + convertToRoman(16));
        System.out.println("№9: " + formula("6 * 4 = 24"));
        System.out.println("№10: " + palindromeDescendant(123312));

    }

}