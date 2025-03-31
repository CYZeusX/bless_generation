package com.CYZco.nygreets;

public class BlessManager extends Bless {
    public String childBless, teenBless, adultBless, elderBless = "";
    public static final String[] STAGES = {"童年", "青年", "成年", "老年"};
    public static final String[] EVENTS = {"新年快樂", "父親節快樂", "母親節快樂", "生日快樂", "身體健康"};
    public static final String[] YOU = {"你", "妳" ,"您"};

    private String[] extractBlesses(String bless) {
        return bless.split(" ");
    }

    public String[] getGreetingsByStage(String stage) {
        return switch (stage) {
            case "童年" -> extractBlesses(this.childBless);
            case "青年" -> extractBlesses(this.teenBless);
            case "成年" -> extractBlesses(this.adultBless);
            case "老年" -> extractBlesses(this.elderBless);
            default -> new String[]{};
        };
    }

    public void setGreetingsByEvent(String event) {
        switch (event) {
            case "新年快樂" -> switchBlessing(childNewYearBless, teenNewYearBless, adultNewYearBless, elderNewYearBless);
            case "父親節快樂" -> switchBlessing(childFathersDayBless, teenFathersDayBless, adultFathersDayBless, elderFathersDayBless);
            case "母親節快樂" -> switchBlessing(childMothersDayBless, teenMothersDayBless, adultMothersDayBless, elderMothersDayBless);
            case "生日快樂" -> switchBlessing(childBirthdayBless, teenBirthdayBless, adultBirthdayBless, elderBirthdayBless);
            case "身體健康" -> switchBlessing(childHealthBless, teenHealthBless, adultHealthBless, elderHealthBless);
            default -> switchBlessing(childNewYearBless, teenNewYearBless, adultNewYearBless, elderNewYearBless);
        };
    }

    private void switchBlessing(String childBless, String teenBless, String adultBless, String elderBless) {
        this.childBless = childBless;
        this.teenBless = teenBless;
        this.adultBless = adultBless;
        this.elderBless = elderBless;
    }
}