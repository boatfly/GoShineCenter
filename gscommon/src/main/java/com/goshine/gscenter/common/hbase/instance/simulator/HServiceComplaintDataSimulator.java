package com.goshine.gscenter.common.hbase.instance.simulator;

import com.goshine.gscenter.common.hbase.instance.entity.HServiceComplaint;
import com.goshine.gscenter.gskit.mapper.JsonMapper;
import com.goshine.gscenter.gskit.time.ClockUtil;
import com.goshine.gscenter.gskit.time.DateFormatUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class HServiceComplaintDataSimulator {

    private static List<String> SLRYs = new ArrayList<>();
    private static List<String> YHMCs = new ArrayList<>();
    private static List<String> CLBMs = new ArrayList<>();
    private static List<String> SQLBs = new ArrayList<>();

    private static List<String> LXDZs = new ArrayList<>();
    private static List<String> SSDWs = new ArrayList<>();

    private static List<String> CONTENTS = new ArrayList<>();

    static{
        SLRYs.add("韦小宝");
        SLRYs.add("陈近南");
        SLRYs.add("段誉");
        SLRYs.add("郭靖");
        SLRYs.add("乔峰");
        SLRYs.add("虚竹");

        YHMCs.add("周文王");
        YHMCs.add("姜子牙");
        YHMCs.add("雷震子");
        YHMCs.add("哪吒");
        YHMCs.add("土地孙");
        YHMCs.add("周武王");

        CLBMs.add("客服1部");
        CLBMs.add("客服2部");
        CLBMs.add("客服3部");
        CLBMs.add("客服4部");
        CLBMs.add("客服5部");
        CLBMs.add("客服6部");

        SQLBs.add("诉求类别1");
        SQLBs.add("诉求类别2");
        SQLBs.add("诉求类别3");
        SQLBs.add("诉求类别4");
        SQLBs.add("诉求类别5");
        SQLBs.add("诉求类别6");

        LXDZs.add("桃花岛一村465弄9527号");
        LXDZs.add("雁门关外路2046号1204室");
        LXDZs.add("恶人谷中心10086号-1室");
        LXDZs.add("少林寺藏经阁95588号-3室");
        LXDZs.add("大理下关洱海私塾");
        LXDZs.add("恒山悬空寺");

        SSDWs.add("武当");
        SSDWs.add("峨眉");
        SSDWs.add("少林");
        SSDWs.add("崆峒");
        SSDWs.add("明教");
        SSDWs.add("华山");

        CONTENTS.add("“杨过知他素来疯疯癫癫，只怕他已然忘了自己，大叫道：“爸爸，是我啊，是你的儿子啊。”这几句话中充满了激情。欧阳锋一呆，拉着他手，将他脸庞转到月光下看去，正是数年来自己到处找寻的义儿，只是一来他身材长高，二来武艺了得，是以初时难以认出。他当即抱住杨过，木叫大嚷：“孩儿，我找得你好苦！”两人紧紧搂在一起，都流下泪来。”");
        CONTENTS.add("“小昭又道：“我命人送各位回归中土，咱们就此别过。小昭身在波斯，日日祝公子福体康宁，诸事顺遂。”说着声音又哽咽了。张无忌道：“你身居虎狼之域，一切小心。”小昭点了点头，吩咐下属备船。谢逊、殷离、赵敏、周芷若等等一一过船。小昭将屠龙刀和倚天剑都交了给张无忌，凄然一笑，举手作别。张无忌不知说甚么话好，呆立片刻，跃入对船。只听得小昭所乘的大舰上号角声呜呜响起，两船一齐扬帆，渐离渐远。但见小昭悄立船头，怔怔向张无忌的座船望着。两人之间的海面越拉越广，终于小昭的座舰成为一个黑点，终于海上一片漆黑，长风掠帆，犹带呜咽之声。 ”");
        CONTENTS.add("赵敏喘了一口长气，骂道：“贼小子，给我着好鞋袜！”张无忌拿起罗袜，一手便握住她左足，刚才一心脱困，意无别念，这时一碰到她温腻柔软的足踝，心中不禁一荡。赵敏将脚一缩，羞得满面通红，幸好黑暗中张无忌也没瞧见，她一声不响的自行穿好鞋袜，在这一霎时之间，心中起了异样的感觉，似乎只想他再来摸一摸自己的脚。");
        CONTENTS.add("欧阳克道：“黄姑娘，多谢你相救。我是活不成了，见到你出力救我，我是死也欢喜。”黄蓉心里忽感歉疚，说道：“你不用谢我。这是我布下的机关，你知道么？”欧阳克低声道：“别这么大声，给叔叔听到了，他可放你不过。我早知道啦，死在你手里，我一点也不怨。”");
        CONTENTS.add("她喜孜孜的回过头来，想要杨过称赞几句。一回头，只见杨过泪流满面，悲不自胜。小龙女一咬牙，只作不见，微笑道：“你说我好不好看？”杨过哽咽道：“好看极了！我给你带上凤冠！”拿起凤冠，走到她身后给她戴上。小龙女在镜中见他举袖擦干了泪水，再到身前时，脸上已作欢容，笑道：“我以后叫你娘子呢，还是仍然叫姑姑？”小龙女心想：“还说什么‘以后’啊？难道咱俩真的还有‘以后’么？”但仍是强作喜色，微笑道：“再叫姑姑自然不好。娘子夫人的，又太老气啦！” 杨过道：“你的小名儿到底叫什么？今天可以说给我听了罢。”小龙女道：“我没小名儿的，师父只叫我作龙儿。”杨过说道：“好，以后你叫我过儿，我便叫你龙儿。咱俩扯个直，谁也不吃亏。等到将来生了孩子，便叫：喂，孩子的爹！喂，孩子的妈！等到孩子大了，娶了媳妇儿……”");
        CONTENTS.add("小龙女听着他这么胡扯，咬着牙齿不住微笑，终于忍耐不住，“哇”的一声，伏在箱子上哭了出来。杨过抢步上前，将她搂在怀里，柔声道：“龙儿，你不好，我也不好，咱们何必理会以后。今天你不会死的，我也不会死。咱俩今儿欢欢喜喜的，谁也不许去想明天的事。”小龙女抬起头来，含泪微笑，点了点头。");
    }

    public static HServiceComplaint generatorOne(){
        HServiceComplaint hServiceComplaint = new HServiceComplaint();
        int rnums = (int)(Math.random()*6);
        //rowkey
        hServiceComplaint.setId("r"+ ClockUtil.currentTimeMillis());
        hServiceComplaint.setSendTime(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND,new Date()));
        //受理内容
        hServiceComplaint.setContent(CONTENTS.get(rnums));
        //工单号
        hServiceComplaint.setGdh(UUID.randomUUID().toString());
        //国网工单号
        hServiceComplaint.setGwgdh(UUID.randomUUID().toString());
        //受理人员
        hServiceComplaint.setSlry(SLRYs.get(rnums));
        //处理人工号
        hServiceComplaint.setSlrgh(UUID.randomUUID().toString());
        //处理部门
        hServiceComplaint.setClbm(CLBMs.get(rnums));
        //处理时间
        hServiceComplaint.setClsj(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND,new Date()));
        //用户编号
        hServiceComplaint.setYhbh(UUID.randomUUID().toString());
        //用户名称
        hServiceComplaint.setYhmc(YHMCs.get(rnums));
        //联系地址
        hServiceComplaint.setLxdz(LXDZs.get(rnums));
        //诉求类别
        hServiceComplaint.setSqlb(SQLBs.get(rnums));
        //所属单位
        hServiceComplaint.setSsdw(SSDWs.get(rnums));

        return hServiceComplaint;
    }

    public static void main(String[] args){
        for(int i=0;i<10;i++){
            HServiceComplaint hServiceComplaint = generatorOne();
            System.out.println(JsonMapper.INSTANCE.toJson(hServiceComplaint));
        }

    }
}
