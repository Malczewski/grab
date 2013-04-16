package edu.sl.grabalyze.main;

import edu.sl.grabalyze.processing.LanguageFactory;
import edu.sl.grabalyze.processing.Stemmer;
import edu.sl.grabalyze.processing.StopWords;
import edu.sl.grabalyze.processing.TextProcessor;
import edu.sl.grabalyze.processing.impl.stemming.RussianStemmer;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        /*ApplicationContext context =
                new ClassPathXmlApplicationContext("edu/sl/grabalyze/config/beans.xml");

        GrabberExecutor executor = (GrabberExecutor) context.getBean("harvester");

        executor.execute();*/
        
        
        String test = "\"Реал\" обіграв \"Галатасарай\" у першому матчі в рамках чвертьфіналу Ліги чемпіонів. Зустріч в Мадриді на стадіоні \"Сантьяго Бернабеу\" завершилася з великим рахунком 3:0."
    +"Голи забили португалець Кріштіану Роналду, француз Карім Бензема і аргентинець Гонсало Ігуаїн. Завдяки сьогоднішньому голу Роналду вийшов на перше місце в гонці бомбардирів в Лізі чемпіонів. У його активі тепер дев'ять голів - на один більше, ніж у нападника \"Барселони\" Ліонеля Мессі і форварда \"Галатасарая\" Бурака Їлмаза."
    +"Матч-відповідь відбудеться 9 квітня в Стамбулі на стадіоні \"Тюрк Телеком - Алі Самі Єн\"."
    +"Паралельний поєдинок \"Малаги\" і \"Боруссії\" завершився без голів - 0:0. Варто відзначити, що до сьогоднішнього дня дортмундська команда, керована Юргеном Клоппом, забивала мінімум один гол протягом 12 матчів Ліги чемпіонів."
    +"Матч-відповідь відбудеться 9 квітня в Дортмунді на стадіоні \"Сигнал Ідуна Парк\"."
    +"Ліга чемпіонів "
    +"1/4 фіналу"
    +"Перший матч"
    +"Реал М (Мадрид, Іспанія) - Галатасарай (Стамбул, Туреччина) - 3:0 "
    +"Голи: Роналду, 9 (1:0); Бензема, 29 (2:0); Ігуаїн, 73 (3:0). "
    +"Попередження: Ессьєн, 84; Рамос, 90; Хедіра, 90 +2 - Нункеу, 41; Мело, 71; Їлмаз, 78; Дрогба, 88."
    +"Малага (Малага, Іспанія) - Боруссія Д (Дортмунд, Німеччина) - 0:0 "
    +"Попередження: Антунеш, 19; Велігтон, 31; Ітурра, 77 - Гросскройц, 17.";
        
        TextProcessor proc = new TextProcessor(LanguageFactory.createRussianLanguage());
        
        System.out.println(proc.processText(test));
        
        
    }
}