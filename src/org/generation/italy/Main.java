package org.generation.italy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

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
		int contatore=0;
		String programma=new String();
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
			put("P01", "prodotto 1");
			put("P02", "prodotto 2");
			put("P03", "prodotto 3");
			put("P04", "prodotto 4");
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
			put("U04", "Spostamento ad altro magazzino");
		}};
		
		//inizia la parte utente con la visualizzazione del menù
		System.out.println("Benvenuto!");
		do {
			//inizia il ciclo che permette di fare più operazioni
			//chiedo quale operazione fare
			movimento= new Movimento();
			System.out.println("Selezionare il programma da usare immettendo il relativo codice");
			System.out.println("\n1) Inserimento movimenti in entrata\n2) Inserimento movimenti in uscita\n3) Visualizza i movimenti in entrata\n4) Visualizzare i movimenti in uscita\n5) Giacenza del prodotto\n6) Esci");
			programma= sc.nextLine();
			
			
			switch (programma) {
			case "1":
				contatore++;//variabile contatore dei movimenti => serve per generare il codice movimento autoincrementante
				//chiedo i dati del movimento
				System.out.println("\nInserire i dati del movimento in entrata: ");
				
				System.out.print("Data - ");
				movimento.data=LocalDate.parse(sc.next(), df);
				sc.nextLine();
				
				movimento.codiceProdotto=verificaCodice(elencoProdotti, sc, "Codice prodotto - ");
						
				System.out.print("Quantità - ");
				movimento.qntProdotto=sc.nextInt();
				sc.nextLine();
				while (movimento.qntProdotto<=0) {
					System.out.println("Inserimento non valido!");
					System.out.print("Quantità - ");
					movimento.qntProdotto=sc.nextInt();
					sc.nextLine();
				}
				
				movimento.codMovimento=contatore;
				System.out.println("Il codice del movimento è: "+movimento.codMovimento);
				
				movimento.codTipologia=verificaCodice(elencoTipologie, sc, "Codice tipologia movimento - ");
				while (movimento.codTipologia.startsWith("U")) {
					//ulteriore controllo che non venga inserito un codice di movimento in uscita
					movimento.codTipologia=verificaCodice(elencoTipologie, sc, "Inserimento non valido! Stai inserendo un'entrata, non un'uscita! Riprova! ");
				}
				if (movimento.codTipologia.equals("E01")) {
					movimento.riferimento=verificaCodice(elencoFornitori, sc, "Riferimento - ");
				} else if (movimento.codTipologia.equals("E02")) {
					movimento.riferimento=verificaCodice(elencoClienti, sc, "Riferimento - ");
				} else {
					movimento.riferimento="";
				}
				elencoEntrate.add(movimento);
				//aggiorno la quantità del prodotto in entrata
				for (String chiave: qntProdotti.keySet()) {
					if (chiave.equals(movimento.codiceProdotto)) {
						
						int qnt=qntProdotti.get(chiave)+movimento.qntProdotto;
						qntProdotti.replace(chiave, qnt);
					}
				}
				break;
			case "2":
				contatore++; //aggiorno il contatore dei movimenti
				//chiedo i dati del movimento in uscita
				System.out.println("Inserire i dati del movimento in uscita: ");
				
				System.out.print("Data - ");
				movimento.data=LocalDate.parse(sc.next(), df);
				sc.nextLine();
				
				movimento.codiceProdotto=verificaCodice(elencoProdotti, sc, "Codice prodotto - ");
				
				System.out.print("Quantità - ");
				movimento.qntProdotto=sc.nextInt();
				sc.nextLine();
				while (movimento.qntProdotto<=0) {
					System.out.println("Inserimento non valido!");
					System.out.print("Quantità - ");
					movimento.qntProdotto=sc.nextInt();
					sc.nextLine();
				}
				
				movimento.codMovimento=contatore;
				System.out.println("Il codice del movimento è: "+movimento.codMovimento);
				
				movimento.codTipologia=verificaCodice(elencoTipologie, sc, "Codice tipologia movimento - ");
				while (movimento.codTipologia.startsWith("E")) {
					//ulteriore controllo che non venga inserito un codice di movimento in entrata
					movimento.codTipologia=verificaCodice(elencoTipologie, sc, "Inserimento non valido! Stai inserendo un'uscita, non un'entrata! Riprova! ");
				}
				if (movimento.codTipologia.equals("U01")) {
					movimento.riferimento=verificaCodice(elencoClienti, sc, "Riferimento - ");
				} else if (movimento.codTipologia.equals("U02")) {
					movimento.riferimento=verificaCodice(elencoFornitori, sc, "Riferimento - ");
				} else {
					movimento.riferimento="";
				}
				
				elencoUscite.add(movimento);
				//aggiorno la quantità del prodotto in uscita
				for (String chiave: qntProdotti.keySet()) {
					if (chiave.equals(movimento.codiceProdotto)) {
						
						int qnt=qntProdotti.get(chiave)-movimento.qntProdotto;
						qntProdotti.replace(chiave, qnt);
					}
				}
				break;
			case "3":
				System.out.println("Elenco dei movimenti in entrata:\n");
				for (int i=0; i<elencoEntrate.size(); i++) {
					//ciclo for per mostrare i vari movimenti in entrata
					System.out.println("Movimento in entrata n°"+(i+1)+":");
					for (String prodotto: elencoProdotti.keySet()) {
						//cerco il nome del prodotto in base alla chiave del prodotto
						if (prodotto.equals(elencoEntrate.get(i).codiceProdotto)) {
							System.out.println("Il prodotto immesso era: "+elencoProdotti.get(prodotto));
						}
					}
					System.out.println("Il prodotto è stato immesso in magazzino in data: "+elencoEntrate.get(i).data.format(df));
					System.out.println("Il prodotto è stato importato nella seguente quantità: "+elencoEntrate.get(i).qntProdotto);
					//decodifico codice entrata
					for (String tipologia: elencoTipologie.keySet()) {
						if (tipologia.equals(elencoEntrate.get(i).codTipologia)) {
							System.out.println(elencoTipologie.get(tipologia));
						}
					}
					//inserimento del riferimento se è presente
					if (!(elencoEntrate.get(i).riferimento.isEmpty()))  { 
						if (elencoEntrate.get(i).codTipologia.equals("E01")) {
							//decodifico il codice del fornitore
							for (String controllo: elencoFornitori.keySet()) {
								if (controllo.equals(elencoEntrate.get(i).riferimento)) {
									System.out.println("Il prodotto è stato acquistato da: "+elencoFornitori.get(controllo));
								}
							}
						} else if (elencoEntrate.get(i).codTipologia.equals("E02")) {
							//decodifico il codice del cliente
							for (String controllo: elencoClienti.keySet()) {
								if (controllo.equals(elencoEntrate.get(i).riferimento)) {
									System.out.println("Il prodotto è stato reso da: "+elencoClienti.get(controllo));
								}
							}
						}
					}
					
				}
				break;
			case "4":
				System.out.println("Elenco dei movimenti in uscita:\n");
				for (int i=0; i<elencoUscite.size(); i++) {
					//ciclo per mostrare i movimenti in uscita
					System.out.println("Movimento in uscita n°"+(i+1)+":");
					for (String prodotto: elencoProdotti.keySet()) {
						//cerco il nome del prodotto in base alla chiave
						if (prodotto.equals(elencoUscite.get(i).codiceProdotto)) {
							System.out.println("Il prodotto esportato era: "+elencoProdotti.get(prodotto));
						}
					}
					System.out.println("Il prodotto è stato esportato dal magazzino in data: "+elencoUscite.get(i).data.format(df));
					System.out.println("Il prodotto è stato esportato nella seguente quantità: "+elencoUscite.get(i).qntProdotto);
					//decodifico il codice dell'uscita
					for (String tipologia: elencoTipologie.keySet()) {
						if (tipologia.equals(elencoUscite.get(i).codTipologia)) {
							System.out.println(elencoTipologie.get(tipologia));
						}
					}
					//stampo il riferimento se presente
					if (!(elencoUscite.get(i).riferimento.isEmpty()))  { 
						if (elencoUscite.get(i).codTipologia.equals("U01")) {
							//decodifico il codice cliente
							for (String controllo: elencoClienti.keySet()) {
								if (controllo.equals(elencoUscite.get(i).riferimento)) {
									System.out.println("Il prodotto è stato venduto a: "+elencoClienti.get(controllo));
								}
							}
						} else if (elencoUscite.get(i).codTipologia.equals("U02")) {
							//decodifico il codice fornitore
							for (String controllo: elencoFornitori.keySet()) {
								if (controllo.equals(elencoUscite.get(i).riferimento)) {
									System.out.println("Il prodotto è stato reso a: "+elencoFornitori.get(controllo));
								}
							}
						}
					}
					
				}
				break;
			case "5":
				
				System.out.println("Come lo vuoi cercare il prodotto?\n1) Nome\n2) Codice\n");
				String ricerca=sc.nextLine().toLowerCase();
				String codice= new String();
				switch (ricerca) {
				case "1":
					//accetto l'inserimento sia del numero che della stringa
				case "nome":
					System.out.print("Inserire il nome del prodotto da cercare: ");
					String nomeCercare=sc.nextLine().toLowerCase();
					while (!elencoProdotti.containsValue(nomeCercare)) {
						System.out.println("Il prodotto cercato non è presente nel database. Riprova con un altro inserimento.");
						nomeCercare=sc.nextLine().toLowerCase();
					}
					codice=trovaChiave(elencoProdotti, nomeCercare);
					break;
				case "2":
					//accetto l'inserimento sia del numero che della stringa
				case "codice":
					codice=verificaCodice(elencoProdotti, sc, "Inserire il codice del prodotto di cui controllare la giacenza: ");
					break;
				default:
					System.out.println("Inserimento non valido!");
				}
				
				//cerco la quantità del prodotto in base al codice o al nome prodotto inserito
				for (String chiave: qntProdotti.keySet()) {
					if (chiave.equals(codice)) {
						System.out.println("La giacenza del prodotto attualmente è: "+qntProdotti.get(chiave));
					}
				}
				break;
			case "6":
				System.out.println("Arrivederci!");
				break;
			default:
				System.out.println("Inserimento non valido.");
				break;
			}
			
			System.out.println("Premere invio per continuare");
			sc.nextLine();
			
		} while (!programma.equals("6"));
		
	}
	private static String verificaCodice (HashMap<String, String> elencoValori, Scanner sc, String messaggio) {
		String codice;
		do {
			System.out.print(messaggio);
			codice=sc.nextLine().toUpperCase();
			
			if(!elencoValori.containsKey(codice)) {
				System.out.println("Codice non valido!");
			}
		}while (!elencoValori.containsKey(codice));
		
		System.out.println("Hai selezionato: "+elencoValori.get(codice));
		
		return codice;
	}
	private static String trovaChiave (HashMap<String, String> elencoValori, String valore) {
		String chiave= new String();
		//scorro per ogni chiave del hashmap cercando quella giusta
		for (String chiaveCercare: elencoValori.keySet()) {
			//confronto il valore per la data chiave con quello inserito dall'utente
			if (valore.equalsIgnoreCase(elencoValori.get(chiaveCercare))) {
				chiave=chiaveCercare; //estraggo la chiave giusta ignorando le altre
			}
		}
		//restituisco la chiave
		return chiave;
	}

}
