import java.util.ArrayList;
import java.util.HashMap;

//Les matrices d'incidence sont beaucoup plus difficiles à concevoir en programmation. Pour les graphes

public class GrapheI {
    private char [][] matriceI;
    private int nbrArcs, nbrSommets, iterations, affectations;
    private ArrayList <Integer> arcsD, arcsA, visitedSommets;
    private HashMap<String, Integer> degres;

    public GrapheI (char [][] matriceI) {
        this.matriceI = matriceI;

    }

    public void evaluation (int sommet) { //À utiliser avant de parcourir le graphe

        visitedSommets = new ArrayList<Integer>();
        arcsD = new ArrayList<Integer>();
        arcsA = new ArrayList<Integer>();
        degres = new HashMap<String, Integer>();

        iterations = 0;
        affectations = 0;
        nbrSommets = 0;
        nbrArcs = 0;

        compteArcsEtSommets();
        trouveDegres();
        parcourir(sommet);

        //System.out.println("Arcs qui forment la voie de l'algorithme parcourir :"+arcsD);
        //System.out.println("Autres arcs que l'algorithme a essayé :"+arcsA);
        System.out.println("Degre de chaque arc: "+degres);
        System.out.println("Nombre de sommets en total: "+nbrSommets);
        System.out.println("Nombre d'arcs en total: "+nbrArcs);
        System.out.println("Iterations: "+iterations);
        System.out.println("Affectations: "+affectations);

        if (!contientCycle()) {
            System.out.println("Il n'existe pas un cycle.");
        } else {
            System.out.println("Il existe un cycle.");
        }

        if (!contientCycleEulerien()) {
            System.out.println("Il n'existe pas un cycle Eulérien.");
        } else {
            System.out.println("Il existe un cycle Eulérien.");
        }

        if (!contientChaineEulerien()) {
            System.out.println("Il n'existe pas une chaine Eulérien.");
        } else {
            System.out.println("Il existe une chaîne Eulérien.");
        }

        if (!estPlanaire()) {
            System.out.println("À partir du nombre de sommets, d'arcs et l'existence d'un tricycle, on peut constater que ce graphe est certainement non planaire.");
        } else {
            System.out.println("À partir du nombre de sommets, d'arcs et l'existence d'un tricycle, " +
                    "\non ne peut pas constater que ce graphe est certainement non planaire. " +
                    "\nIl faut évaluer ce graphe avec un algorithme basé sur le théorème de Kuratowski," +
                    "\nou un algorithme comme celui de Hopcroft et Tarjan.");
        }

    }

    private boolean contientCycle () {
        /*
        S'il existe des arcs qui mènent à des sommets déjà explorés, un cycle existe
         */

        //Identifier les sous graphes
        HashMap<String, ArrayList<Integer>> sousGraphes = identifieSousGraphes();
        boolean ce = false;

        //Pour chaque sous graphe
        for (int i = 0; i < sousGraphes.size(); i++) {

            visitedSommets.clear();
            arcsD.clear();
            arcsA.clear();
            //parcourir le sous graphe
            parcourir(sousGraphes.get(("Graphe " + i)).get(0));

            if (arcsA.size() != 0) {
                return true;
            }

            //Pour chaque sommet découvert dans le sous graphe
        }


        return false;
    };

    private boolean contientCycleEulerien () {

        //Examiner si le graphe est connexe
        if (identifieSousGraphes().size()!=1) {
            return false;
        }

        trouveDegres();
        for (int i = 0; i < degres.size(); i++) {

            int deg = degres.get("S"+i);
            if (deg%2!=0 || deg == 0) {return false;} // Si degré impair ou sommet isolé

            return true;

        }

        //HashMap<String, ArrayList<Integer>> sousGraphes = identifieSousGraphes();
        //boolean ce = false;

        /*
        for (int i = 0; i < sousGraphes.size(); i++) {
            int degreSousGraphe = 0;
            int nbrSommetsSousGraphe;
            ce = true;

            visitedSommets.clear();
            arcsD.clear();
            arcsA.clear();
            //parcourir le sous graphe
            parcourir(sousGraphes.get(("Graphe " + i)).get(0));

            //Pour chaque sommet découvert dans le sous graphe
            for (int in : visitedSommets) {

                //Pour chaque pair de sommet et degrés

                degreSousGraphe = degres.get("S" + in);

                //System.out.println(degreSousGraphe);
                //Si le degré n'est pas pair
                if (!(degreSousGraphe%2==0)||degreSousGraphe==0) {
                    ce = false;
                    break; //Essayer un autre sous graphe
                }

            }
        }

         */
        return true;
    }

    private boolean contientChaineEulerien () {
        int impair = 0;

        //Examiner si le graphe est connexe
        if (identifieSousGraphes().size()!=1) {
            return false;
        }

        trouveDegres();
        //Pour chaque sommet
        for (int i = 0; i < degres.size(); i++) {

            //Trouve le degré
            int deg = degres.get("S"+i);
            if (deg%2!=0 && deg != 0) { // Si degré impair
                impair++;
            }

            if (impair>2) { // Si la totale des degrés impairs est supérieure à 2
                return false; // Ce ne contient pas une chaîne eulérienne
            }

        }

        if (impair==2) { // S'il contient exactement deux
            return true; // Ce contient une chaîne eulérienne
        } else {
            return false;
        }

    };

    private boolean estPlanaire () {
        /*
        Des conditions de planarité pour un graphe simple connexe
        Condition 1 : a ≤ 3s − 6, s ≥ 3
        Condition 2 : a ≤ 2s − 4, s ≥ 3, si aucun cycle de 3
        Condition 3 : degré moyen < 6
        s'il existe des sous graphes, vérifie chacun
        */
        HashMap <String, ArrayList<Integer>> sousGraphes = identifieSousGraphes();

        //Pour chaque sous graphe
        for ( int i = 0; i < sousGraphes.size(); i++) {
            int degreSousGrapheTotal = 0;
            int nbrSommetsSousGraphe;

            visitedSommets.clear();
            arcsD.clear();
            arcsA.clear();
            //parcourir le sous graphe
            parcourir (sousGraphes.get(("Graphe "+i)).get(0));

            //Pour chaque sommet découvert dans le sous graphe
            for (int in : visitedSommets) {

                //Pour chaque pair de sommet et degrés

                degreSousGrapheTotal += degres.get("S"+in);

            }

            //Examiner condition 3
            //System.out.println(visitedSommets);
            nbrSommetsSousGraphe = visitedSommets.size();
            if (!((degreSousGrapheTotal/nbrSommetsSousGraphe)<6)) {return false;}
            //System.out.println(nbrSommetsSousGraphe);
            //System.out.println(degreSousGrapheTotal);
            //System.out.println("cond3");

            //Examiner condition 2
            compteArcsEtSommetsSG();
            if (!(trouveTricycle())&&!((nbrArcs<=(2*nbrSommets)-4))&&nbrSommets>=3) {return false;}
            //System.out.println((nbrArcs<=(2*nbrSommets)-4));

            //Examiner condition 1
            if ((trouveTricycle())&&!(nbrArcs<=(3*nbrSommets)-6)) {return false;}
            //System.out.println("cond1");

        }

        return true;
    }

    private HashMap <String, ArrayList<Integer>> identifieSousGraphes () {

        HashMap <String, ArrayList<Integer>> graphes = new HashMap <String, ArrayList<Integer>> (); //Entreposage d'un graphe connexe seul ou des sous graphes dérivés d'un graphe non connexe
        ArrayList<Integer> doub = new ArrayList<Integer>();

        for (int i = 0; i < matriceI.length; i++) {
            visitedSommets.clear();
            arcsD.clear();
            arcsA.clear();

            //Traverser
            parcourir(i);
            //System.out.println(visitedSommets);
            //System.out.println(i);
            ArrayList<Integer> liste = new ArrayList<Integer>();

            for (Integer in : visitedSommets) {
                int c = in;

                liste.add(c);
            }

            //Vérifier si "graphes" ne contient pas de graphe
            if (graphes.isEmpty()) {
                //System.out.println("isempty");
                graphes.put("Graphe "+(i), liste);
            }

            //Comparaison entre sous graph potentiel et les autres sous graphes (s'ils existent)
            for (int ii = 0; ii < graphes.size(); ii++) {
                //System.out.println((graphes.get("Graphe "+ii).size()==liste.size()));
                //System.out.println(graphes.get("Graphe "+ii));
                //System.out.println(liste);

                //Vérifier si "graphes" ne contient pas ce graphe déjà

/*
                //Si les graphes ne sont pas de même taille, ils sont différents
                if (!(graphes.get("Graphe "+ii).size()==(visitedSommets).size())) {
                    graphes.put("Graphe "+(ii+1), visitedSommets);
                    break;
                } else {

 */
                    //Pour chaque valeur dans la clé actuelle de "graphes"
                    for ( int iii = 0; iii < liste.size(); iii++) {
                        //System.out.println(iii);
                        if (!(doub.contains(liste.get(iii)))) {
                            //System.out.println(liste.get(iii));
                            //System.out.println(graphes.get("Graphe " + ii));
                            //System.out.println((graphes.get("Graphe " + ii).contains(liste.get(iii))));
                            //Comparer chaque valeur, si toutes les valeurs ne sont pas identiques, les graphes sont différents
                            if (!(graphes.get("Graphe " + ii).contains(liste.get(iii)))) {
                                graphes.put("Graphe " + (ii + 1), liste);
                                //System.out.println("ajoutée");
                                doub.add(liste.get(iii));
                                //System.out.println(doub);
                                break;
                            }
                            doub.add(liste.get(iii));
                            //System.out.println(doub);
                        }
                    }
  //              }
            }

        }



        return graphes;
    }

    private boolean trouveTricycle () {
        /*
        Pour chaque sommet :
        1. Vérifie si le sommet a deux enfants
        2. Vérifie si les enfants sont connectés

        arrête quand il trouve un tricycle
         */

        //Pour chaque sommet
        for (int sommet : visitedSommets) {
            ArrayList <Integer> fils = new ArrayList<Integer>();

            //Si degré > 1

            //Pour chaque arc d'un sommet
            for (int arc : arcsIncidents(sommet)) {
                //Ajoute le sommet coincident
                fils.add(chercheIncidence(sommet, arc));


            }

            //Pour chaque fils

            for (int f : fils) {
                ArrayList <Integer> freres = new ArrayList<Integer>();
                //ajoute les frères
                for (int arc : arcsIncidents(f)) {
                    //Ajoute le sommet coincident
                    freres.add(chercheIncidence(f, arc));


                }
                int c = 0;
                for (int fr : freres) {
                    //Si le fils coincide avec au moins un autre fils
                    if (fils.contains(fr)) {
                        c++;
                        if (c == 2) { //Coincide avec le père et l'autre fils
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void trouveDegres () {
        String clef;
        int degre;
        degres.clear();

        for (int i = 0; i < nbrSommets; i++) {
            clef = "S"+i;
            degre = 0;
            for (int ii = 0; ii < nbrArcs; ii++) {
                if (matriceI[i][ii] == 1) {
                    degre++;
                }
            }
            degres.put(clef, degre);
        }
    }

    private void parcourir (int sommet) {
        if (!(visitedSommets.contains(sommet))) {
            visitedSommets.add(sommet);
        }
        affectations++;
        /*
        for(int i : visitedSommets) {
            System.out.print(i+" ");
        }
         */
        for (int i = 0; i < arcsIncidents(sommet).size(); i++) {
            iterations++;
            //System.out.println(!(arcsA.contains(arcsIncidents(sommet)[i])) || !(arcsD.contains(arcsIncidents(sommet)[i])));
            //System.out.println(arcsIncidents(sommet).get(i));
            if (!(arcsA.contains(arcsIncidents(sommet).get(i))) && !(arcsD.contains(arcsIncidents(sommet).get(i)))) { //si l'arc n'a pas été visité
                //cherche pour l'autre sommet qui coincide

                int sommetAdj = chercheIncidence(sommet, arcsIncidents(sommet).get(i));
                affectations++;
                //System.out.println(sommetAdj);
                if (!(visitedSommets.contains(sommetAdj))) {
                    //System.out.println("ArcsD :"+arcsD);
                    arcsD.add(arcsIncidents(sommet).get(i));
                    affectations++;
                    parcourir (sommetAdj);

                } else {
                    //System.out.println("ArcsA :"+arcsA);
                    arcsA.add(arcsIncidents(sommet).get(i));
                    affectations++;

                }
            }

        }


       // return 0;
    }

    //La méthode parcourir() dépend sur toutes les méthodes au-dessous, donc la totale d'itérations et affectations inclura les itérations et affectations de chaque méthode ci-dessous ainsi que celles de parcourir()

    private ArrayList<Integer> arcsIncidents (int sommet) { //Parcourir le matrice d'incidence pour trouver un sommet qui coincide avec le sommet donné comme argument
        int c = 0;
        affectations++;
        ArrayList <Integer> arcs = new ArrayList<Integer>();
        affectations++;

        for (int i = 0; i < matriceI[sommet].length; i++) {
            iterations++;
            if (matriceI[sommet][i] == 1) {
                //System.out.println(i);
                arcs.add(i);
                affectations++;
            }
        }

        return arcs;

    }

    private int chercheIncidence (int sommet, int arc) { //Parcourir le matrice d'incidence pour trouver les arcs associés avec un sommet
        int c=0;
        affectations++;
        int adjacent=0;
        affectations++;

        do {
            iterations++;
            if (matriceI[sommet++][arc] == 1) {
               // System.out.println("true");
                c++;
                affectations++;
                if (c==2) {
                    //System.out.println("truetrue");
                    adjacent = --sommet;
                    affectations++;
                }
            }

            if (sommet > matriceI.length-1) {
                sommet = 0;
                affectations++;
            }


        } while (c<2);
        //System.out.println(c);

        return adjacent;

    }

    private void compteArcsEtSommets () {
        nbrSommets = 0;
        nbrArcs = 0;

        for (int i = 0; i < matriceI.length; i++) {
            iterations++;
            nbrSommets++;
        }
        for (int i = 0; i < matriceI[0].length; i++) {
            iterations++;
            nbrArcs++;
        }
    }

    private void compteArcsEtSommetsSG () {
        nbrSommets = 0;
        nbrArcs = 0;

        for (int e : visitedSommets) {
            nbrSommets++;
        }

        for (int en : arcsD) {
            nbrArcs++;
        }

        for (int ent : arcsA) {
            nbrArcs++;
        }
    }

    /*
    private float calculerDegreMoyen () {
        float somme = 0;
        for (int d : degres.values()) {
            somme += d;
        }
        return somme/degres.values().size();
    }
     */

}

