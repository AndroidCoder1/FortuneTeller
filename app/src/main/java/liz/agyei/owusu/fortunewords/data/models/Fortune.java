
package liz.agyei.owusu.fortunewords.data.models;

import java.util.List;

import liz.agyei.owusu.fortunewords.utils.Configs;

public class Fortune {

    private List<String> fortune;

    public void setFortuneList(List<String> fortuneList) {
        this.fortune = fortuneList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(fortune != null) {
            for (String item : fortune) {
                sb.append(item + "\n");
            }
        }else{
            sb.append(Configs.DEFAULT_ADVICE);
        }
        return sb.toString();
    }
}
