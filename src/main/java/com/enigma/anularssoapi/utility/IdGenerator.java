package com.enigma.anularssoapi.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class IdGenerator {

    private static String STATIC_KEY;

    @Value("WeAreStrong")
    public void setStaticKey(String value){
        IdGenerator.STATIC_KEY = value;
    }

    public String getTypeId(){
        return encode() + simpleEncode(Integer.toString(randomNumber())) + encode();
    }

    public String getGroupId(Integer id, String user){
        StringBuilder group = new StringBuilder(Integer.toHexString(id));
        while(group.length() <= 5){
            group.append("0");
        }
        return simpleEncode(group.toString()) +
                "-" +
                encode() +
                user +
                simpleEncode(Integer.toString(randomNumber()));
    }

    public String getUserId(Integer id, String group){
        StringBuilder user = new StringBuilder(Integer.toHexString(id));
        while(user.length() <= 5){
            user.append("0");
        }
        return simpleEncode(user.toString()) +  "-" + group + "-" + encode();
    }

    public String simpleEncode(String code) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            char x = STATIC_KEY.charAt(i);
            int y = Integer.parseInt(code.substring(i, i+1), 16);
            int a = getCharacters().chars().filter((T) -> T == x).findAny().getAsInt();
            string.append(
                    getCharacters().charAt(
                            (getCharacters().indexOf(a) + 3 + y) % getCharacters().length()
                    )
            );
        }
        return string.toString();
    }

    public String simpleDecode(String code){
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < code.length(); i++){
            int value = code.charAt(i);
            int a = getCharacters().chars().filter((T) -> T == value).findAny().getAsInt();
            string.append(
                    Integer.toHexString(
                            ((getCharacters().length() + (getCharacters().indexOf(a) - 3)) % getCharacters().length()) - getCharacters().indexOf(STATIC_KEY.charAt(i))
                    ).toUpperCase()
            );
        }
        return string.toString();
    }

    public String encode() {
        StringBuilder result = new StringBuilder();
        Arrays.stream(Long.toString(System.currentTimeMillis()).split(""))
                .map(Integer::parseInt)
                .forEach(integer -> {
                    integer = getNumber(integer);
                    while (true){
                        if(getCharacters().contains(((Character)((char)(int) integer)).toString())){
                            result.append((char)(int) integer);
                            break;
                        } else {
                            integer = getNumber(integer);
                        }
                    }
                });
        return result.toString();
    }

    private String getCharacters() {
        return "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }

    private int getNumber(int x) {
        return ((x * (int) STATIC_KEY.charAt(randomNumber() % STATIC_KEY.length())) + randomNumber()) % 123;
    }

    private int randomNumber() {
        return Math.abs((int)(Math.random()*10));
    }

}
