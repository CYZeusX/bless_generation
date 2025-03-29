package com.CYZco.nygreets;

public class BlessManager extends Bless {

    public String childBless, teenBless, adultBless, elderBless = "";
    public final String[] STAGES = {"童年", "青年", "成年", "老年"};
    public final String[] FESTIVALS = {"新年快樂", "父親節快樂", "母親節快樂", "生日快樂", "身體健康"};
    public final String[] YOU = {"你", "妳" ,"您"};

    private String[] extractBlesses(String bless) {
        return bless.split(" ");
    }

    public String[] getGreetingsByStage(String stage) {
        return switch (stage) {
            case "童年" -> extractBlesses(this.childNewYearBless);
            case "青年" -> extractBlesses(this.teenNewYearBless);
            case "成年" -> extractBlesses(this.adultNewYearBless);
            case "老年" -> extractBlesses(this.elderNewYearBless);
            default -> new String[]{};
        };
    }

    public void switchBlessing(String replaceChildBless, String replaceTeenBless, String replaceAdultBless, String replaceElderBless) {
        childBless = replaceChildBless;
        teenBless = replaceTeenBless;
        adultBless = replaceAdultBless;
        elderBless = replaceElderBless;
    }
}