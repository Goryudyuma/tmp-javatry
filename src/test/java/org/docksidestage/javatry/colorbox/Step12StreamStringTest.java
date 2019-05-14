/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.colorbox;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.color.BoxColor;
import org.docksidestage.javatry.colorbox.base.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, using Stream API you can. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author kei.matsumoto
 */
public class Step12StreamStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * What is color name length of first color-box? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = colorBoxList.stream().findFirst().map(colorBox -> colorBox.getColor()) // consciously split as example
                .map(boxColor -> boxColor.getColorName()).map(colorName -> {
                    log(colorName); // for visual check
                    return String.valueOf(colorName.length());
                }).orElse("not found"); // basically no way because of not-empty list and not-null returns
        log(answer);
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .reduce("", (String s1, String s2) -> {
                    return s1.length() > s2.length() ? s1 : s2;
                });
        log(ans);
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Integer> collects = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString().length())
                .collect(Collectors.toList());

        Integer maxV = Collections.max(collects);
        Integer minV = Collections.min(collects);
        log(maxV - minV);
    }

    /**
     * Which value (toString() if non-string) has second-max legnth in color-boxes? (without sort)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (ソートなしで))
     */
    public void test_length_findSecondMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> collects = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content != null)
                .map(content -> content.toString())
                .collect(Collectors.toList());
        Integer maxV = Collections.max(collects.stream().map(collect -> collect.length()).collect(Collectors.toList()));
        List<String> filterd = collects.stream().filter(collect -> collect.length() != maxV).collect(Collectors.toList());
        log(filterd.stream().reduce("", (String s1, String s2) -> {
            if (s1.length() > s2.length())
                return (s1);
            else
                return (s2);
        }));
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Integer ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString().length())
                .reduce(0, (s1, s2) -> {return s1 + s2;});
        log(ans);
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String ans = colorBoxList.stream().map(colorBox -> colorBox.getColor().toString()).reduce("", (String s1, String s2) -> {
            if (s1.length() > s2.length()) {
                return s1;
            } else {
                return s2;
            }
        });
        log(ans);
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<BoxColor> ans = colorBoxList.stream().filter(colorBox -> colorBox.getSpaceList().stream().anyMatch(boxSpace -> {
            Object content = boxSpace.getContent();
            if (content instanceof String) {
                return content.toString().startsWith("Water");
            }
            return false;
        })).map(colorBox -> colorBox.getColor()).collect(Collectors.toList());
        log(ans);
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<BoxColor> ans = colorBoxList.stream().filter(colorBox -> colorBox.getSpaceList().stream().anyMatch(boxSpace -> {
            Object content = boxSpace.getContent();
            if (content instanceof String) {
                return content.toString().endsWith("front");
            }
            return false;
        })).map(colorBox -> colorBox.getColor()).collect(Collectors.toList());
        log(ans);
    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * What number character is starting with "front" of string ending with "front" in color-boxes? <br>
     * (あなたのカラーボックスに入ってる "front" で終わる文字列で、"front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Integer> ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .filter(content -> content.toString().endsWith("front"))
                .map(content -> ((String) content).lastIndexOf("front") + 1)
                .collect(Collectors.toList());
        log(ans);
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (あなたのカラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Integer> ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(content -> content.lastIndexOf("ど") != content.indexOf("ど"))
                .map(content -> content.lastIndexOf("ど") + 1)
                .collect(Collectors.toList());
        log(ans);
    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Character> ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(content -> content.endsWith("front"))
                .map(content -> content.charAt(0))
                .collect(Collectors.toList());
        log(ans);
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Character> ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(content -> content.startsWith("Water"))
                .map(content -> content.charAt(content.length() - 1))
                .collect(Collectors.toList());
        log(ans);
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Integer> ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(content -> content.contains("o"))
                .map(content -> content.replace("o", ""))
                .map(content -> content.length())
                .collect(Collectors.toList());
        log(ans);
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof File)
                .map(content -> ((File) content).getPath())
                .map(content -> content.replace("/", "\\"))
                .collect(Collectors.toList());
        log(ans);
    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Integer ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof YourPrivateRoom.DevilBox)
                .map(content -> {
                    try {
                        ((YourPrivateRoom.DevilBox) content).wakeUp();
                        ((YourPrivateRoom.DevilBox) content).allowMe();
                        ((YourPrivateRoom.DevilBox) content).open();
                        return ((YourPrivateRoom.DevilBox) content).getText().length();
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .reduce(0, (Integer i1, Integer i2) -> {return i1 + i2;});
        log(ans);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public void test_showMap_flat() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(content -> content.getContent())
                .filter(content -> content instanceof java.util.Map)
                .forEach(content -> log("map:" + content.toString().replace(",", ";")));
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(content -> content.getContent())
                .filter(content -> content instanceof java.util.Map)
                .forEach(content -> log("map:" + content.toString().replace(",", ";").replace("={", "=map:{ ").replace("=", " = ")));

    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    // too difficult to be stream?
    /**
     * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_flat() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Map<String, Object>> ans = colorBoxList.stream()
                .filter(content -> content.getColor().toString().equals("{white}"))
                .map(colorBox -> colorBox.getSpaceList().get(0))
                .map(boxSpace -> (YourPrivateRoom.SecretBox) boxSpace.getContent())
                .map(content -> stringToMap(content.getText()))
                .collect(Collectors.toList());
        log(ans);
    }

    private Map<String, Object> stringToMap(String S) {
        Map<String, Object> ret = new HashMap<>();

        String sRaw = S.substring(5, S.length() - 1).trim();
        int beginPoint = 0;

        int level = 0;
        for (int i = 0; i < sRaw.length(); i++) {
            char c = sRaw.charAt(i);
            if (c == '{') {
                level++;
            } else if (c == '}') {
                level--;
            } else if (c == ';' && level == 0) {
                String ss = sRaw.substring(beginPoint, i).trim();
                String key = ss.substring(0, ss.indexOf("=") - 1).trim();
                String value = ss.substring(ss.indexOf("=") + 1).trim();

                if (value.startsWith("map:")) {
                    ret.put(key, stringToMap(value));
                } else {
                    ret.put(key, value);
                }
                beginPoint = i + 1;
            }
        }
        String ss = sRaw.substring(beginPoint).trim();
        String key = ss.substring(0, ss.indexOf("=") - 1).trim();
        String value = ss.substring(ss.indexOf("=") + 1).trim();

        if (value.startsWith("map:")) {
            ret.put(key, stringToMap(value));
        } else {
            ret.put(key, value);
        }
        return ret;
    }

    /**
     * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_nested() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        String[] objects = colorBoxList.stream()
                .filter(colorBox -> colorBox.getColor().toString().equals("{white}"))
                .map(colorBox -> colorBox.getSpaceList())
                .map(spaceList -> (new String[] { ((YourPrivateRoom.SecretBox) spaceList.get(1).getContent()).getText(),
                        ((YourPrivateRoom.SecretBox) spaceList.get(2).getContent()).getText() }))
                .collect(Collectors.toList())
                .get(0);

        log(objects[0]);
        log(stringToMap(objects[0]).toString());
        log(objects[1]);
        log(stringToMap(objects[1]).toString());

    }
}
