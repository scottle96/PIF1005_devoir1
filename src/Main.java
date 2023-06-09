public class Main {
    public static void main(String[] args) {

        //Représenter les graphes comme des matrices. J'ai pensé à utiliser HashMap
        //ou une collection des objets de sommet pour représenter un graphe, mais une
        //matrice est beaucoup plus facile à traiter dans le code.

        //Graphe 1, Matrice adjacence, sommets > arcs donc il y a plus d'affectations de 0
        char [][] matriceA_Un = {
                {0,0,0,0,1},
                {0,0,1,0,0},
                {0,1,0,1,0},
                {0,0,1,0,0},
                {1,0,0,0,0}
        };
        //Graphe 1, Matrice incidence, beaucoup moins d'affectations de 0 donc c'est plus efficace pour représenter ce graphe
        char [][] matriceI_Un = {
                {1,0,0},
                {0,1,0},
                {0,1,1},
                {0,0,1},
                {1,0,0}
        };


        //Graphe 2, Matrice adjacence, beaucoup moins d'affectations de 0 donc c'est plus efficace pour représenter ce graphe
        char [][] matriceA_Deux = {
                {0,1,0,0,0,1},
                {1,0,1,1,0,1},
                {0,1,0,1,0,0},
                {0,1,1,0,1,1},
                {0,0,0,1,0,1},
                {1,1,0,1,1,0}
        };
        //Graphe 2, Matrice incidence, sommets < arcs donc il y a plus d'affectations de 0
        char [][] matriceI_Deux = {
                {1,0,0,0,0,0,0,0,1},
                {1,1,0,0,0,0,1,1,0},
                {0,1,1,0,0,0,0,0,0},
                {0,0,1,1,0,1,1,0,0},
                {0,0,0,1,1,0,0,0,0},
                {0,0,0,0,1,1,0,1,1},

        };

        //Graphe 3, Matrice adjacence, qui représente un graphe K(n) qui produit une matrice adjacence idéale à utiliser dans notre programme,
        char [][] matriceA_Trois = {
                {0,1,1,1,1},
                {1,0,1,1,1},
                {1,1,0,1,1},
                {1,1,1,0,1},
                {1,1,1,1,0},
        };
        //Graphe 3, Matrice incidence, sommets < arcs donc il y a plus d'affectations de 0
        char [][] matriceI_Trois = {
                {1,0,0,0,1,1,0,0,0,1},
                {1,1,0,0,0,0,1,1,0,0},
                {0,1,1,0,0,1,0,0,1,0},
                {0,0,1,1,0,0,0,1,0,1},
                {0,0,0,1,1,0,1,0,1,0}

        };

        //Graphe 4, Matrice adjacence, une matrice presque idéale à utiliser dans notre programme
        char [][] matriceA_Quatre = {
                {0,1,1,1,0},
                {1,0,1,1,1},
                {1,1,0,1,1},
                {1,1,1,0,1},
                {0,1,1,1,0},
        };
        //Graphe 4, Matrice incidence, sommets < arcs donc il y a plus d'affectations de 0
        char [][] matriceI_Quatre = {
                {1,0,0,0,1,0,0,0,1},
                {1,1,0,0,0,1,1,0,0},
                {0,1,1,0,1,0,0,1,0},
                {0,0,1,1,0,0,1,0,1},
                {0,0,0,1,0,1,0,1,0}

        };

        GrapheA grapheA_Un = new GrapheA(matriceA_Un);
        GrapheI grapheI_Un = new GrapheI(matriceI_Un);

        grapheA_Un.evaluation(0);
        grapheI_Un.evaluation(0);

        GrapheA grapheA_Deux = new GrapheA(matriceA_Deux);
        GrapheI grapheI_Deux = new GrapheI(matriceI_Deux);

        grapheA_Deux.evaluation(0);
        grapheI_Deux.evaluation(0);

        GrapheA grapheA_Trois = new GrapheA(matriceA_Trois);
        GrapheI grapheI_Trois = new GrapheI(matriceI_Trois);

        grapheA_Trois.evaluation(0);
        grapheI_Trois.evaluation(0);

        GrapheA grapheA_Quatre = new GrapheA(matriceA_Quatre);
        GrapheI grapheI_Quatre = new GrapheI(matriceI_Quatre);

        grapheA_Quatre.evaluation(0);
        grapheI_Quatre.evaluation(0);

    }
}