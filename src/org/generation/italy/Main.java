package org.generation.italy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.generation.italy.model.Movimento;

class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DateTimeFormatter df=DateTimeFormatter.ofPattern("dd/MM/yyyy");
		//ArrayList <Movimento> elencoMovimenti= new ArrayList<Movimento>();
		ArrayList <Movimento> elencoEntrate= new ArrayList<Movimento>();
		ArrayList <Movimento> elencoUscite= new ArrayList<Movimento>();
		Movimento movimento;
		String risposta= new String();
		int programma;
		boolean trovato=false;
		Scanner sc= new Scanner(System.in);
		HashMap <String, String> elencoFornitori= new HashMap<String, String>() {{
			put("F01", "Fornitore 1");
			put("F02", "Fornitore 2");
			put("F03", "Fornitore 3");
			put("F04", "Fornitore 4");
		}};
		HashMap <String, String> elencoClienti= new HashMap<String, String>(){{
			put("C01", "Cliente 1");
			put("C02", "Cliente 2");
			put("C03", "Cliente 3");
			put("C04", "Cliente 4");
		}};
		HashMap <String, String> elencoProdotti= new HashMap<String, String>(){{
			put("P01", "Prodotto 1");
			put("P02", "Prodotto 2");
			put("P03", "Prodotto 3");
			put("P04", "Prodotto 4");
		}};
		HashMap <String, Integer> qntProdotti= new HashMap<String, Integer>(){{
			put("P01", 0);
			put("P02", 0);
			put("P03", 0);
			put("P04", 0);
		}};
		HashMap <String, String> elencoTipologie= new HashMap<String, String>(){{
			put("E01", "Acquisto da fornitore");
			put("E02", "Reso da cliente");
			put("E03", "Produzione interna");
			put("E04", "Spostamento da altro magazzino");
			put("U01", "Vendita a cliente");
			put("U02", "Reso a fornitore");
			put("U03", "Sostituzione in garanzia");
			put("U04", "Spostamento a altro magazzino");
		}};
		
		//inizia la parte utente con la visualizzazione del menù
		System.out.println("Benvenuto!");
		do {
			movimento= new Movimento();
			System.out.println("Selezionare il programma da usare immettendo il relativo codice");
			System.out.println("\n1) Inserimento movimenti in entrata\n2) Inserimento movimenti in uscita\n3) Visualizza i movimenti in entrata\n4) Visualizzare i movimenti in uscita\n5) Giacenza del prodotto");
			programma= sc.nextInt();
			sc.nextLine();
			while (programma!=1&&programma!=2&&programma!=3&&programma!=4&&programma!=5) {
				System.out.println("Inserimento non valido.\n1) Inserimento movimenti in entrata \n2) Inserimento movimenti in uscita \n3) Visualizza i movimenti in entrata \n4) Visualizzare i movimenti in uscita \n5) Giacenza del prodotto");
				programma=sc.nextInt();
				sc.nextLine();
			}
			
			if (programma==1) {
				System.out.println("Inserimento i dati del movimento in entrata: ");
				System.out.print("Data - ");
				movimento.data=LocalDate.parse(sc.next(), df);
				sc.nextLine();
				System.out.print("Codice prodotto - ");
				movimento.codiceProdotto=sc.nextLine();
				System.out.print("Quantità - ");
				movimento.qntProdotto=sc.nextInt();
				sc.nextLine();
				movimento.codMovimento++;
				System.out.println("Il codice del movimento è: "+movimento.codMovimento);
				System.out.println("Codice tipologia movimento - ");
				movimento.codTipologia=sc.nextLine();
				System.out.print("Riferimento (opzionale) - ");
				movimento.riferimento=sc.nextLine(); 
				//elencoMovimenti.add(movimento);
				elencoEntrate.add(movimento);
				for (String chiave: qntProdotti.keySet()) {
					if (chiave.equals(movimento.codiceProdotto)) {
						
						int qnt=qntProdotti.get(chiave)+movimento.qntProdotto;
						qntProdotti.replace(chiave, qnt);
					}
				}
			} else if (programma==2) {
				System.out.println("Inserimento i dati del movimento in uscita: ");
				System.out.print("Data - ");
				movimento.data=LocalDate.parse(sc.next(), df);
				sc.nextLine();
				System.out.print("Codice prodotto - ");
				movimento.codiceProdotto=sc.nextLine();
				System.out.print("Quantità - ");
				movimento.qntProdotto=sc.nextInt();
				sc.nextLine();
				movimento.codMovimento++;
				System.out.println("Il codice del movimento è: "+movimento.codMovimento);
				System.out.println("Codice tipologia movimento - ");
				movimento.codTipologia=sc.nextLine();
				System.out.print("Riferimento (opzionale) - ");
				movimento.riferimento=sc.nextLine();
				//elencoMovimenti.add(movimento);
				elencoUscite.add(movimento);
				for (String chiave: qntProdotti.keySet()) {
					if (chiave.equals(movimento.codiceProdotto)) {
						
						int qnt=qntProdotti.get(chiave)-movimento.qntProdotto;
						qntProdotti.replace(chiave, qnt);
					}
				}
			} else if (programma==3) {
				System.out.println("Elenco dei movimenti in entrata:\n");
				for (int i=0; i<elencoEntrate.size(); i++) {
					System.out.println("Movimento in entrata n°"+(i+1)+":");
					for (String prodotto: elencoProdotti.keySet()) {
						if (prodotto.equals(elencoEntrate.get(i).codiceProdotto)) {
							System.out.println("Il prodotto immesso era: "+elencoProdotti.get(prodotto));
						}
					}
					System.out.println("Il prodotto è stato immesso in magazzino in data: "+elencoEntrate.get(i).data.format(df));
					System.out.println("Il prodotto è stato importato nella seguente quantità: "+elencoEntrate.get(i).qntProdotto);
					for (String tipologia: elencoTipologie.keySet()) {
						if (tipologia.equals(elencoEntrate.get(i).codTipologia)) {
							System.out.println(elencoTipologie.get(tipologia));
						}
					}
					if (!(elencoEntrate.get(i).riferimento.equals("")))  { 
						if (elencoEntrate.get(i).codTipologia.equals("E01")) {
							for (String controllo: elencoFornitori.keySet()) {
								if (controllo.equals(elencoEntrate.get(i).riferimento)) {
									System.out.println("Il prodotto è stato acquistato da: "+elencoFornitori.get(controllo));
								}
							}
						} else if (elencoEntrate.get(i).codTipologia.equals("E02")) {
							for (String controllo: elencoClienti.keySet()) {
								if (controllo.equals(elencoEntrate.get(i).riferimento)) {
									System.out.println("Il prodotto è stato reso da: "+elencoClienti.get(controllo));
								}
							}
						} else if (elencoEntrate.get(i).codTipologia.equals("E03")) {
							System.out.println("Il prodotto è stato fabbricato internamente.");
						} else if (elencoEntrate.get(i).codTipologia.equals("E04")) {
							System.out.println("Il prodotto è stato spostato da un altro magazzino");
						}
					}
					
				}
				
			} else if (programma==4) {
				System.out.println("Elenco dei movimenti in uscita:\n");
				for (int i=0; i<elencoUscite.size(); i++) {
					System.out.println("Movimento in uscita n°"+(i+1)+":");
					for (String prodotto: elencoProdotti.keySet()) {
						if (prodotto.equals(elencoUscite.get(i).codiceProdotto)) {
							System.out.println("Il prodotto esportato era: "+elencoProdotti.get(prodotto));
						}
					}
					System.out.println("Il prodotto è stato esportato dal magazzino in data: "+elencoUscite.get(i).data.format(df));
					System.out.println("Il prodotto è stato esportato nella seguente quantità: "+elencoUscite.get(i).qntProdotto);
					for (String tipologia: elencoTipologie.keySet()) {
						if (tipologia.equals(elencoUscite.get(i).codTipologia)) {
							System.out.println(elencoTipologie.get(tipologia));
						}
					}
					if (!(elencoEntrate.get(i).riferimento.equals("")))  { 
						if (elencoEntrate.get(i).codTipologia.equals("U01")) {
							for (String controllo: elencoClienti.keySet()) {
								if (controllo.equals(elencoEntrate.get(i).riferimento)) {
									System.out.println("Il prodotto è stato venduto a: "+elencoClienti.get(controllo));
								}
							}
						} else if (elencoEntrate.get(i).codTipologia.equals("U02")) {
							for (String controllo: elencoClienti.keySet()) {
								if (controllo.equals(elencoEntrate.get(i).riferimento)) {
									System.out.println("Il prodotto è stato reso a: "+elencoFornitori.get(controllo));
								}
							}
						} else if (elencoEntrate.get(i).codTipologia.equals("E03")) {
							System.out.println("Il prodotto è stato sostituito in garanzia.");
						} else if (elencoEntrate.get(i).codTipologia.equals("U04")) {
							System.out.println("Il prodotto è stato spostato in un altro magazzino");
						}
					}
					
				}
				
			} else if (programma==5) {
				System.out.print("Inserire il codice del prodotto di cui controllare la giacenza:");
				String codice=sc.nextLine();
				
				for (String chiave: qntProdotti.keySet()) {
					if (chiave.equals(codice)) {
						System.out.println("La giacenza del prodotto attualmente è: "+qntProdotti.get(chiave));
					}
				}
				
			}
			
			System.out.println("Vuoi fare altro?");
			risposta=sc.nextLine();
		} while (risposta.substring(0, 1).equals("s"));
	}

}
