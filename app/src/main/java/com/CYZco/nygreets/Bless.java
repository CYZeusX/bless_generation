package com.CYZco.nygreets;

public class Bless
{
    public final String[] STAGES = {"(選階段)","童年","青年","成年","老年"};
    public final String[] FESTIVALS = {"新年","父親節","母親節","生日"};

    private String kidBless =
        "心想事成\uD83D\uDCAB 大吉大利\uD83C\uDF4A 身體健康\uD83D\uDCAA 學業進步\uD83C\uDF93 步步高升\uD83D\uDCC8 萬事如意\uD83C\uDF40 家庭幸福\uD83C\uDFE0 天天向上\uD83D\uDCC8 夢想成真\uD83C\uDF20 福星高照\uD83C\uDF1F 鵬程萬里\uD83E\uDD85 鶴立雞群\uD83D\uDC51 " +
        "吉祥安康\uD83C\uDF08 青春常駐\uD83C\uDF1E 笑口常開\uD83D\uDE04 一帆風順\u200b\u26F5 喜氣洋洋\uD83D\uDE01 龍馬精神\uD83D\uDC32 鴻運當頭\uD83C\uDF40 如意吉祥\uD83D\uDCAB 歡樂無邊\uD83D\uDE0A 金榜題名\uD83C\uDFC6 福氣滿滿\uD83D\uDC96 前程似錦\u200b\u2728 " +
        "彩雲追月\uD83C\uDF19 福如東海\uD83C\uDF0A 壽比南山\u26f0\uFE0F 萬事勝意\uD83C\uDFAF 春風得意\uD83C\uDF43 人見人愛\uD83E\uDD70 學業有成\uD83D\uDCD6 事事順心\uD83C\uDF08 福祿雙全\uD83C\uDF89 日進斗金\uD83E\uDE99 龍騰虎躍\uD83D\uDCAA 年年高升\uD83D\uDE80 " +
        "歡樂年年\uD83C\uDF8A 天天開心\uD83C\uDF1E 快樂無限\uD83C\uDF08 諸事順利\uD83D\uDCAB 繁花似錦\uD83C\uDF38 四季平安\uD83D\uDE0C 平安快樂\uD83D\uDE07 成長茁壯\uD83C\uDF31 五福臨門\uD83C\uDF40 快高長大\uD83C\uDF33 百事可樂\uD83D\uDE04 如虎添翼\uD83E\uDEBD";

    private String teenBless =
        "成長茁壯\uD83C\uDF31 心想事成\uD83D\uDCAB 身體健康\uD83D\uDCAA 學業進步\uD83C\uDF93 萬事如意\uD83C\uDF40 步步高升\uD83D\uDCC8 家庭幸福\uD83C\uDFE0 財源滾滾\uD83D\uDC8E 夢想成真\uD83C\uDF20 福星高照\uD83C\uDF1F 吉祥安康\uD83C\uDF08 青春常駐\uD83C\uDF1E " +
        "前途無量\uD83D\uDE80 青雲直上\u200b\u2601 如日方升\uD83C\uDF1E 天天向上\uD83D\uDCC8 勇往直前\uD83C\uDFC3 一帆風順\u200b\u26F5 福氣滿滿\uD83D\uDC96 才華橫溢\uD83C\uDF1F 前途光明\uD83D\uDD06 天道酬勤\uD83E\uDD78 歡樂年年\uD83C\uDF8A 如意吉祥\uD83D\uDCAB " +
        "笑口常開\uD83D\uDE04 喜氣洋洋\uD83D\uDE01 龍馬精神\uD83D\uDC32 鴻運當頭\uD83C\uDF40 笑容滿面\u200b\u263A 快高長大\uD83C\uDF33 金榜題名\uD83C\uDFC6 事事順心\uD83C\uDF08 前程似錦\u200b\u2728 大展鴻圖\uD83D\uDCAA 四季平安\uD83D\uDE0C 平安喜樂\uD83D\uDE07 " +
        "百事順利\uD83D\uDCAB 美夢成真\uD83C\uDF20 青春無敵\uD83D\uDD25 飛黃騰達\uD83E\uDD85 蓬勃發展\uD83D\uDCC8 如虎添翼\uD83E\uDEBD 諸事順利\uD83D\uDCAB 學業進步\uD83C\uDF93 才貌雙全\uD83D\uDE0E 學業有成\uD83D\uDCD6 龍騰虎躍\uD83D\uDCAA 家和萬事興\uD83E\uDD29";

    private String adultBless =
        "青春常駐\uD83C\uDF1E 萬事如意\uD83C\uDF40 身體健康\uD83D\uDCAA 財源廣進\uD83D\uDCB5 步步高升\uD83D\uDCC8 闔家平安\u200b\u262E️ 心想事成\uD83D\uDCAB 平安喜樂\uD83D\uDE07 前程似錦\u200b\u2728 一帆風順\u200b\u26F5 大展鴻圖\uD83D\uDCAA 福星高照\uD83C\uDF1F " +
        "迎春納福\uD83C\uDF43 開門見喜\uD83C\uDF89 喜氣洋洋\uD83D\uDE01 祥瑞滿滿\uD83C\uDE35 前途光明\uD83D\uDD06 龍馬精神\uD83D\uDC32 心花怒放\uD83C\uDF38 金玉滿堂\uD83C\uDFE6 前程無限\u200b\u267E️ 天天順心\uD83D\uDE0C 家庭幸福\uD83C\uDFE0 和氣生財\uD83E\uDDD8 " +
        "富貴有餘\uD83D\uDCC8 福如東海\uD83C\uDF0A 壽比南山\u26f0\ufe0f 五福臨門\uD83C\uDF40 萬象更新\uD83D\uDCA8 龍騰虎躍\uD83D\uDCAA 如意吉祥\uD83D\uDCAB 開運亨通\uD83D\uDCB0 八方來財\uD83D\uDCB5 年年有餘\uD83D\uDC21 事事順利\uD83E\uDD1E 歡樂年年\uD83C\uDF8A " +
        "豐衣足食\uD83C\uDF5A 富貴滿堂\uD83D\uDC8E 年年高升\uD83D\uDE80 招財進寶\uD83E\uDDE7 日進斗金\uD83E\uDE99 光明燦爛\u200b\u2600 四季平安\uD83D\uDE0C 鴻運當頭\uD83C\uDF40 財源滾滾\uD83D\uDC8E 事業有成\uD83D\uDCCA 如虎添翼\uD83E\uDEBD 家和萬事興\uD83E\uDD29";

    private String elderBless =
        "身體健康\uD83D\uDCAA 長命百歲\uD83D\uDCAF 如意吉祥\uD83D\uDCAB 福壽安康\uD83E\uDDD8 萬事如意\uD83C\uDF40 心想事成\uD83D\uDCAB 福星高照\uD83C\uDF1F 壽與天齊\u200b\u23F3 龍馬精神\uD83D\uDC32 平安喜樂\uD83D\uDE07 老當益壯\uD83D\uDCAA 萬壽無疆\u200b\u2728 " +
        "喜氣洋洋\uD83D\uDE01 長壽康寧\uD83D\uDE0C 安享晚年\uD83E\uDD20 福氣滿滿\uD83D\uDC96 壽比南山\u26f0\ufe0f 笑口常開\uD83D\uDE04 天倫之樂\uD83D\uDE06 身體康泰\uD83D\uDD7A 快樂長伴\uD83D\uDE04 寶刀未老\uD83E\uDDBE 歲歲平安\u262E\uFE0F 年年有餘\uD83D\uDC21 " +
        "松鶴延年\uD83E\uDDA9 鴻運當頭\uD83C\uDF40 歡樂年年\uD83C\uDF8A 金玉滿堂\uD83C\uDFE6 喜迎新歲\uD83C\uDF38 吉祥安康\uD83C\uDF08 和樂融融\uD83D\uDE07 延年益壽\u200b\u23EB 年年健康\uD83E\uDD29 大吉大利\uD83C\uDF4A 財源廣進\uD83D\uDCB5 心境常寧\u200b\u263A " +
        "闔家平安\u262E\uFE0F 家庭幸福\uD83C\uDFE0 五福臨門\uD83C\uDF40 四季平安\uD83D\uDE0C 豐衣足食\uD83C\uDF5A 福壽綿延\u200b\u23F3 和睦安康\uD83E\uDD1D 美滿生活\u200b\u2728 福壽雙全\uD83D\uDE46 平安長樂\uD83D\uDE4F 富貴有餘\uD83D\uDCC8 福如東海\uD83C\uDF0A";

    private String[] extractBlesses(String bless)
    {
        return bless.split(" ");
    }

    public String[] getGreetingsByStage(String stage)
    {
        switch (stage)
        {
            case "童年": return extractBlesses(kidBless);
            case "青年": return extractBlesses(teenBless);
            case "成年": return extractBlesses(adultBless);
            case "老年": return extractBlesses(elderBless);
            default: return new String[]{};
        }
    }
}
