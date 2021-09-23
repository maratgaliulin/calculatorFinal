package com.example.java;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.*;

public class Calculator {

    public static String stringToArab (String s){
        if(checkValidRoman(s)){
            int ar = 0;
            int last_dgt = 0;
            int curr_dgt = 0;

            for(int i = 0; i < s.length(); i++){
                if(s.charAt(i) == 'I'){
                    curr_dgt = 1;
                }
                if(s.charAt(i) == 'V'){
                    curr_dgt = 5;
                }
                if(s.charAt(i) == 'X'){
                    curr_dgt = 10;
                }
                if(s.charAt(i) == 'L'){
                    curr_dgt = 50;
                }
                if(s.charAt(i) == 'C'){
                    curr_dgt = 100;
                }
                if(s.charAt(i) == 'D'){
                    curr_dgt = 500;
                }
                if(s.charAt(i) == 'M'){
                    curr_dgt = 1000;
                }

                if(last_dgt < curr_dgt && last_dgt != 0){
                    curr_dgt -= last_dgt;
                    ar -= last_dgt;
                    ar += curr_dgt;
                    curr_dgt = 0;
                } else {
                    last_dgt = curr_dgt;
                    ar += curr_dgt;
                    curr_dgt = 0;
                }

            }

            return String.valueOf(ar);
        }else {
            return null;
        }
    }

    public static boolean checkValidRoman(String inp){
        for (int i = 0; i < inp.length(); i++) {
            if (inp.charAt(i) != 'I' &&
                    inp.charAt(i) != 'V' &&
                    inp.charAt(i) != 'X'
            ) {
                return false;
            }
        }

        return true;

    }

    public static boolean checkValidArabic(String n){
        for (int i = 0; i < n.length(); i++){
            if(!Character.isDigit(n.charAt(i))){
                return false;
            }
        }
        if(Integer.parseInt(n) > 100 || Integer.parseInt(n) < 1){
            return false;
        }
        return true;
    }

    public static String stringToRoman(String n){
        if(checkValidArabic(n)){
            String rom = "";
            int num = Integer.parseInt(n);

            String onesArray[] = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
            String tensArray[] = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
            String hundredsArray[] = {"C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};

            int ones = num % 10;

            num = (num - ones) / 10;
            int tens = num % 10;

            num = (num - tens) / 10;
            int hundreds = num % 10;

            num = (num - hundreds)/10;

            for(int i = 0; i < num; i++){
                rom += "M";
            }

            if (hundreds >= 1){
                rom += hundredsArray[hundreds - 1];
            }

            if(tens >= 1){
                rom += tensArray[tens - 1];
            }

            if(ones >= 1){
                rom += onesArray[ones - 1];
            }

            return String.valueOf(rom);


        }else {
            return null;
        }
    }

    public static LinkedList stringToArray(String str){

        int result = 0;
        double resDouble = 0;
        String regex = "(\\d+\\.\\d+)|(\\d+)|([+-/*///^])|([/(/)])|M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})";

        String regArab = "^(\\d+\\.\\d+)|(\\d+)";
        String regRoman = "^(M{0,4})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})";

        Matcher m = Pattern.compile(regex).matcher(str);

        LinkedList list = new LinkedList();
        LinkedList res1 = new LinkedList();

        while (m.find()) {
            list.add(m.group());
        }

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals("") || list.get(i).equals(" ") || list.get(i).equals(null) || list.get(i).equals(",")){
                list.remove(i);
            }
        }


        if(list.size() == 3){
            if(list.get(0).toString().matches(regArab) && list.get(2).toString().matches(regArab) && Integer.parseInt(list.get(0).toString()) >= 1 && Integer.parseInt(list.get(0).toString()) <= 10){


                if(list.get(1).toString().equals("*")){
                    result = Integer.parseInt(list.get(0).toString()) * Integer.parseInt(list.get(2).toString());
                    res1.add(Integer.toString(result));
                } else if(list.get(1).toString().equals("/")){
                    BigDecimal divided = new BigDecimal(Double.parseDouble(list.get(0).toString()));
                    BigDecimal divider = new BigDecimal(Double.parseDouble(list.get(2).toString()));
                    BigDecimal bigRes = divided.divide(divider).setScale(0, RoundingMode.DOWN);
                    res1.add(bigRes);
                } else if(list.get(1).toString().equals("+")){
                    result = Integer.parseInt(list.get(0).toString()) + Integer.parseInt(list.get(2).toString());
                    res1.add(Integer.toString(result));
                } else if(list.get(1).toString().equals("-")){
                    result = Integer.parseInt(list.get(0).toString()) - Integer.parseInt(list.get(2).toString());
                    res1.add(Integer.toString(result));
                } else {
                    System.out.println("Выбрано неверное действие");
                }


            }else if(list.get(0).toString().matches(regRoman)
                    && list.get(2).toString().matches(regRoman)
                    && checkValidRoman(list.get(0).toString())
                    && checkValidRoman(list.get(2).toString())
                    && Integer.parseInt(stringToArab(list.get(0).toString())) >= 1
                    && Integer.parseInt(stringToArab(list.get(0).toString())) <= 10
                    && Integer.parseInt(stringToArab(list.get(2).toString())) >= 1
                    && Integer.parseInt(stringToArab(list.get(2).toString())) <= 10){


                if(list.get(1).toString().equals("*")){
                    result = Integer.parseInt(stringToArab(list.get(0).toString())) * Integer.parseInt(stringToArab(list.get(2).toString()));
                    res1.add(stringToRoman(Integer.toString(result)));
                } else if(list.get(1).toString().equals("/")){
                    BigDecimal dividedRom = new BigDecimal(Double.parseDouble(stringToArab(list.get(0).toString())));
                    BigDecimal dividerRom = new BigDecimal(Double.parseDouble(stringToArab(list.get(2).toString())));
                    BigDecimal bigResRom = dividedRom.divide(dividerRom).setScale(0, RoundingMode.DOWN);
                    res1.add(stringToRoman(bigResRom.toString()));
                    resDouble = Double.parseDouble(stringToArab(list.get(0).toString())) / Double.parseDouble(stringToArab(list.get(2).toString()));
                    res1.add(stringToRoman(Double.toString(resDouble)));
                } else if(list.get(1).toString().equals("+")){
                    result = Integer.parseInt(stringToArab(list.get(0).toString())) + Integer.parseInt(stringToArab(list.get(2).toString()));
                    res1.add(stringToRoman(Integer.toString(result)));
                } else if(list.get(1).toString().equals("-")){
                    result = Integer.parseInt(stringToArab(list.get(0).toString())) - Integer.parseInt(stringToArab(list.get(2).toString()));
                    res1.add(stringToRoman(Integer.toString(result)));
                } else {
                    System.out.println("Выбрано неверное действие");
                }

            }
            else {
                System.out.println("ОШИБКА: Введенные значения не соответствуют параметрам ввода");
                return null;
            }

        }else {
            System.out.println("Ошибка, неверный формат строки или введено более одного арифметического действия");
            return null;
        }

        return res1;

    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str;
        System.out.println("Введите строку: ");
        str = scan.nextLine();

        try{
            LinkedList arr = stringToArray(str);

            if(arr.get(0) != null){
                System.out.println("Результат: " + arr.get(0));
            }else {
                System.out.println("Ошибка, что-то пошло не так");
            }
        }catch (Exception e){
            System.out.println("Системная ошибка, приложение завершает работу");
        }


    }

}
