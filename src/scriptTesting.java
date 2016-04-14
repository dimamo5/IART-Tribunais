import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sonhs on 11/04/2016.
 */
public class scriptTesting {

    public HashMap<String,String> getValues() throws Exception{
        URL url = new URL("http://www.imovirtual.com/estatisticas/mercado-imobiliario/");
        Document doc = Jsoup.parse(url,5000);

        int l = doc.getElementById("linkmaps").getElementsByTag("li").size();

        ArrayList<String> distritos = new ArrayList<String>();
        HashMap<String,String> valores = new HashMap<>();

        //get distritos
        for(int i = 0; i < l ; i++) {

            String c = doc.getElementById("linkmaps").getElementsByTag("li").get(i).select("a").attr("href").split("(http://www.imovirtual.com/estatisticas/mercado-imobiliario/)")[1];
            String f = "http://www.imovirtual.com/estatisticas/mercado-imobiliario/" + URLEncoder.encode(c,"UTF-8");
            f = f.replace("%2F","/");
            distritos.add(f);
            System.out.println(distritos.get(i));
        }

        //aceder a cada distrito
        for(int i = 0; i < distritos.size(); i++){
            url = new URL(distritos.get(i)); //trocar para i
            doc = Jsoup.parse(url,99999999);
            String nome, val;

            l = doc.getElementsByClass("statistics_district_wrap").get(0).getElementsByTag("div").get(0).select("a").size();

            //concelho a concelho
            for(int j = 0; j < l; j++ ){
                String c = doc.getElementsByClass("statistics_district_wrap").get(0).getElementsByTag("div").get(0).select("a").get(j).attr("href");
                String f =  "http://www.imovirtual.com/"+ URLEncoder.encode(c,"UTF-8");
                f = f.replaceAll("%2F","/");

                URL url_concelho = new URL(f);
                Document doc_concelho = Jsoup.parse(url_concelho,9999999);

                nome = doc_concelho.getElementsByClass("mb20").get(0).ownText().split("(Concelho de )")[1];

                String x[] = doc_concelho.getElementById("browse_quotes").getElementsByTag("tbody").get(0).getElementsByTag("tr").get(2).getElementsByTag("td").get(1).ownText().split("€ ");

                if(x.length == 1)
                    val = "600";
                else
                    val  = doc_concelho.getElementById("browse_quotes").getElementsByTag("tbody").get(0).getElementsByTag("tr").get(2).getElementsByTag("td").get(1).ownText().split("€ ")[1];
                System.out.println(nome + " " + val);

                valores.put(nome.replaceAll("\\+"," "),val);
            }
        }

        System.out.println(valores.toString());

        return valores;
    }


    public static void main(String[] args) throws Exception
    {
        //scriptTesting s = new scriptTesting();
        //s.getValues();
        /*String s = "Alijó=600, Vila Flor=600, Grândola=2.915, Vila do Porto=600, Meda=600, Moura=656, Covilhã=608, Marvão=600, Gouveia=600, Mesão Frio=600, Nelas=438, Calheta (Madeira)=600, Avis=600, Vila do Conde=600, Sernancelhe=600, Mondim de Basto=600, Benavente=1.109, Marinha Grande=600, Lourinhã=956, Caldas da Rainha=600, Alvito=600, Loures=1.350, Aveiro=919, Torres Novas=600, Penamacor=600, Tomar=869, Barrancos=600, Sabugal=600, Miranda do Douro=600, Portalegre=600, Ourém=705, Tabuaço=600, Tarouca=600, Crato=600, Ribeira Grande=600, Vimioso=600, Fronteira=600, Almeida=600, Vila Nova de Paiva=600, Peso da Régua=600, Idanha-a-Nova=600, Cantanhede=860, Condeixa-a-Nova=815, Alfandega da Fé=600, Ponte da Barca=600, Vila Pouca de Aguiar=600, Santa Cruz da Graciosa=600, Coruche=858, Marco de Canaveses=600, Tondela=537, Ovar=963, Viseu=770, Arronches=600, Paredes=842, Aljustrel=600, Entroncamento=883, Leiria=846, Fornos de Algodres=600, Seixal=1.292, Cuba=687, Sintra=1.733, Amarante=786, Montalegre=686, Faro=2.017, Matosinhos=1.259, Moimenta da Beira=600, Paços de Ferreira=600, Vila Viçosa=600, Velas=705, Ponte de Sor=600, Monchique=600, Lagos=2.558, Mafra=1.287, Angra do Heroísmo=600, Valongo=917, Rio Maior=600, Castelo de Vide=600, Castanheira de Pêra=600, Baião=829, Montemor-o-Novo=1.659, Peniche=1.093, Ribeira Brava=600, Vinhais=600, Oeiras=2.178, Valpaços=600, Mealhada=785, Pedrógão Grande=600, Odemira=1.435, Batalha=870, Lamego=600, Cadaval=722, Silves=1.596, Armamar=600, Mértola=788, Mação=600, Machico=597, Celorico de Basto=600, Montemor-o-Velho=761, Viana do Castelo=600, Almeirim=804, Santa Marta de Penaguião=600, Penedono=600, Vila de Rei=600, São Brás de Alportel=600, Ílhavo=995, Beja=765, Coimbra=1.068, Almodôvar=600, Tavira=1.873, Santarém=839, Loulé=2.591, Oliveira de Frades=600, Santa Maria da Feira=600, São Roque do Pico=600, Mira=913, Santana=941, Freixo de Espada à Cinta=600, Vila Nova de Gaia=600, Vila Verde=600, Lisboa=3.266, Almada=1.537, Aljezur=1.392, Alcanena=520, Ferreira do Alentejo=600, Salvaterra de Magos=600, Belmonte=600, Miranda do Corvo=600, Reguengos de Monsaraz=600, Nazaré=1.423, Felgueiras=1.036, Vieira do Minho=600, Trofa=892, Porto Moniz=600, Castro Marim=600, Carregal do Sal=600, Povoa De Varzim=600, Águeda=645, Alcoutim=730, Golegã=631, Praia da Vitória=600, Barcelos=1.098, Serpa=648, Santa Cruz das Flores=600, Penacova=656, Amadora=1.410, Vendas Novas=600, Viana do Alentejo=600, Caminha=1.413, Fafe=719, Manteigas=600, Gondomar=940, Cabeceiras de Basto=600, Cinfães=748, Elvas=1.033, Pinhel=600, Barreiro=1.015, Vagos=840, Campo Maior=600, Óbidos=1.419, Alcochete=1.364, Monção=600, Azambuja=829, Lousada=694, Sardoal=600, Oliveira de Azeméis=600, Figueira da Foz=600, São João da Pesqueira=600, Aguiar da Beira=600, Mirandela=600, Ferreira do Zêzere=600, Pampilhosa da Serra=600, Ponte de Lima=600, Seia=600, Alcácer do Sal=600, Bragança=579, Calheta (São Jorge)=600, Vila Nova de Foz Côa=600, Vila Nova de Cerveira=600, São João da Madeira=600, Ponta do Sol=600, Sabrosa=600, Figueiró dos Vinhos=600, Santa Comba Dão=600, Tábua=625, Abrantes=745, Penela=776, Sátão=600, Nisa=600, Vila Nova de Famalicão=600, Porto de Mós=600, Mora=600, Redondo=471, Lagoa (Algarve)=600, Câmara de Lobos=600, Castelo de Paiva=600, Vila Real de Santo António=600, Nordeste=705, Albergaria-a-Velha=705, Vila do Bispo=600, Braga=932, Sever do Vouga=600, Penafiel=812, Vila Franca de Xira=600, Chamusca=600, Lajes das Flores=600, Figueira de Castelo Rodrigo=600, Anadia=666, Guimarães=834, Gavião=592, Borba=600, Porto=1.728, Porto Santo=600, Cascais=2.631, Póvoa de Lanhoso=600, Macedo de Cavaleiros=600, Torres Vedras=600, Corvo=645, Lajes do Pico=600, Mogadouro=600, Portimão=1.601, Sobral de Monte Agraço=600, Celorico da Beira=600, Arraiolos=675, Fundão=478, Montijo=980, Arganil=468, Carrazeda de Ansiães=600, Chaves=734, Castelo Branco=600, Góis=600, Palmela=1.141, Penalva do Castelo=600, Lagoa (São Miguel)=600, Monforte=600, Cartaxo=730, Castro Daire=600, Ansião=667, Ribeira de Pena=600, Mangualde=535, Constância=600, Soure=643, Boticas=600, Estarreja=695, Alpiarça=877, Mourão=600, Oliveira do Bairro=600, Évora=1.104, Trancoso=600, Madalena=705, Alvaiázere=636, Arcos de Valdevez=600, Guarda=678, Valença=828, Olhão=1.573, Arruda dos Vinhos=600, Odivelas=1.332, Santa Cruz=600, Torre de Moncorvo=600, Vila Real=600, Setúbal=1.271, Sesimbra=1.318, Ponta Delgada=600, Castro Verde=600, Oleiros=600, Pombal=717, Melgaço=600, São Pedro do Sul=600, Ourique=600, Murtosa=732, Alter do Chão=600, Alandroal=712, Alcobaça=946, Mortágua=600, Estremoz=600, Vidigueira=600, Portel=564, Santo Tirso=600, Bombarral=737, Terras de Bouro=600, Lousã=697, Moita=1.179, Espinho=1.064, Maia=1.015, Paredes de Coura=600, Sertã=600, Albufeira=2.094, Alenquer=815, Arouca=597, Murça=600, Esposende=1.196, Vizela=828, Proença-a-Nova=600, Horta=645, Amares=724, Povoação=597, Oliveira do Hospital=600, Resende=600, Vouzela=607, Santiago do Cacém=600, Funchal=666";
        //System.out.println(s.split("(, )").length);
        for(String c : s.split("(, )" ))
            System.out.println(c);*/
        String c = "lagoa=1992.8";
        Double d = Double.parseDouble(c.split("=")[1]);
        System.out.println(d);
    }
}
