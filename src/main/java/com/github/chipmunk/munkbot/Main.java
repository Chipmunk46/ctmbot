package com.github.chipmunk.munkbot;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.Message;

public class Main {

	public static void main(String[] args) throws IOException {
		DiscordApi api = new DiscordApiBuilder().setToken("NTU3Njg3Njg1MzcyNzA2ODE4.XMTEiQ.kY2mjv3SRnRoBLvkUm_60oGnexQ").login().join();
		System.out.println("Munkbot logged in!");
		System.out.println(api.createBotInvite());
		
		String prefix = "$c";
		Database data = new Database();
		// Load in previous maps/series
		
		// Load in MessageCreate Listeners
		api.addMessageCreateListener(e -> {
			// Load in commands
			String[] comArgs = e.getMessage().getContent().split(" ");
			
			if (comArgs[0].equals(prefix)) {
				
				switch(comArgs[1]) {
				// help
				case "help":
					if (comArgs.length == 2) {
						EmbedBuilder helpAll = new EmbedBuilder()
								.setAuthor("Help - All")
								.addField("help (command)", "Explains how to use a command.")
								.addField("call", "Calls a specific map or series from the master list.")
								.addField("list", "Calls a page from the master map list or master series list.")
								.addField("random", "Calls a random map or series from their respective master lists.")
								.addField("add", "Adds a map or series to their respective master list.")
								.addField("delete", "Deletes a map or series from the masterlist.")
								.addField("edit", "Edits a map or series in the masterlist.")
								.setThumbnail("http://i.imgur.com/XEmaKVo.png")
								.setColor(Color.ORANGE);
							e.getChannel().sendMessage(helpAll);
					} else {
					switch (comArgs[2]) {
				
					case "all":
						EmbedBuilder helpAll = new EmbedBuilder()
							.setAuthor("Help - All")
							.addField("help (command)", "Explains how to use a command.")
							.addField("call", "Calls a specific map or series from the master list.")
							.addField("list", "Calls a page from the master map list or master series list.")
							.addField("random", "Calls a random map or series from their respective master lists.")
							.addField("add", "Adds a map or series to their respective master list.")
							.addField("delete", "Deletes a map or series from the masterlist.")
							.addField("edit", "Edits a map or series in the masterlist.")
							.setThumbnail("http://i.imgur.com/XEmaKVo.png")
							.setColor(Color.ORANGE);
						e.getChannel().sendMessage(helpAll);
						break;
					case "call":
						EmbedBuilder helpCall = new EmbedBuilder()
							.setAuthor("Help - Call")
							.addField("Command Usage", "$c call (map/series) (map/series name) | Ex: $c call map Corona_Trials")
							.addField("Command Function", "Calls a specific map or series from the master list. Map/Series names must have all spaces replaced with underscores (_)")
							.setThumbnail("http://i.imgur.com/XEmaKVo.png")
							.setColor(Color.ORANGE);
						e.getChannel().sendMessage(helpCall);
						break;
					case "list":
						EmbedBuilder helpList = new EmbedBuilder()
							.setAuthor("Help - List")
							.addField("Command Usage", "$c list (maps/series) (page #)| Ex: $c list maps 0")
							.addField("Command Function", "Calls a page from the master map list or master series list.")
							.setThumbnail("http://i.imgur.com/XEmaKVo.png")
							.setColor(Color.ORANGE);
						e.getChannel().sendMessage(helpList);
						break;
					case "random":
						EmbedBuilder helpRandom = new EmbedBuilder()
							.setAuthor("Help - Random")
							.addField("Command Usage", "$c random (map/series)| Ex: $c random map")
							.addField("Command Function", "Calls a random map or series from their respective master lists.")
							.setThumbnail("http://i.imgur.com/XEmaKVo.png")
							.setColor(Color.ORANGE);
						e.getChannel().sendMessage(helpRandom);
						break;
					case "add":
						EmbedBuilder helpAdd = new EmbedBuilder()
						.setAuthor("Help - Add")
						.addField("Command Usage", "$c add map (series name) (map name) | Ex: $c add map Untold_Stories Corona_Trials")
						.addField("Command Usage", "$c add series (series name")
						.addField("Command Function", "Adds a map or series to their respective master list. Map/Series names must have all spaces replaced with underscores (_)")
						.setThumbnail("http://i.imgur.com/XEmaKVo.png")
						.setColor(Color.ORANGE);
						e.getChannel().sendMessage(helpAdd);
						break;
					case "delete":
						EmbedBuilder helpDelete = new EmbedBuilder()
						.setAuthor("Help - Delete")
						.addField("Command Usage", "$c delete (map/series) (map/series name)| Ex: $c delete map Corona_Trials")
						.addField("Command Function", "Deletes a map or series from the masterlist. Map/Series names must have all spaces replaced with underscores (_)")
						.setThumbnail("http://i.imgur.com/XEmaKVo.png")
						.setColor(Color.ORANGE);
						e.getChannel().sendMessage(helpDelete);
						break;
					case "edit":
						EmbedBuilder helpEdit = new EmbedBuilder()
						.setAuthor("Help - Edit")
						.addField("Command Usage", "$c edit map (name/description/version/length/series/image/download) (map name) (input value) | Ex: $c edit map description Corona_Trials This_is_a_map!")
						.addField("Command Usage", "$c edit series (name/author/image/description) (series name) (input value) | Ex: $c edit series author Untold_Stories RenderXR")
						.addField("Command Function", "Edits a map or series in the masterlist. Map/Series names and input values must have all spaces replaced with underscores (_). image must be a direct link")
						.setThumbnail("http://i.imgur.com/XEmaKVo.png")
						.setColor(Color.ORANGE);
						e.getChannel().sendMessage(helpEdit);
						break;
						}
					}
					
					break;
					
				// call
				case "call":
					if (comArgs.length == 2){
						EmbedBuilder callError = new EmbedBuilder()
								.setAuthor("Error")
								.setDescription("There was an error! Make sure your command is formatted correctly.");
						e.getChannel().sendMessage(callError);
					} else {
					switch (comArgs[2]) {
					case "map":
						if (comArgs.length >= 3) {
							e.getChannel().sendMessage(data.getMap(data.getMapIndexNumber(comArgs[3])).getEmbed());
						}
						break;
					case "series":
						if (comArgs.length >= 3) {
							e.getChannel().sendMessage(data.getSeries(data.getSeriesIndexNumber(comArgs[3])).getSeriesEmbed());
							
						}
						break;
					}
					}
					break;
					
				// list
				case "list":
					if (comArgs.length == 2) {
						EmbedBuilder listError = new EmbedBuilder()
								.setAuthor("Error")
								.setDescription("There was an error! Make sure your command is formatted correctly.");
						e.getChannel().sendMessage(listError);
					} else {
					switch (comArgs[2]) {
					case "maps":
						if (comArgs.length >= 3) {
							int mapListIndex = Integer.parseInt(comArgs[3]);
							e.getChannel().sendMessage(data.getMapListPage(mapListIndex));
						}
						break;
					case "series":
						if (comArgs.length >= 3) {
							int seriesListIndex = Integer.parseInt(comArgs[3]);
							e.getChannel().sendMessage(data.getSeriesListPage(seriesListIndex));
						}
						break;
					}
					}
					break;
					
				// random
				case "random":
					if (comArgs.length == 2) {
						EmbedBuilder randomError = new EmbedBuilder()
								.setAuthor("Error")
								.setDescription("There was an error! Make sure your command is formatted correctly.");
						e.getChannel().sendMessage(randomError);
					} else {
					switch (comArgs[2]) {
					case "map":
						Random  randM = new Random();
						int randIndexM = randM.nextInt(data.getMapList().size());
						e.getChannel().sendMessage(data.getMap(randIndexM).getEmbed());
						break;
					case "series":
						Random  randS = new Random();
						int randIndexS = randS.nextInt(data.getSeriesList().size());
						e.getChannel().sendMessage(data.getSeries(randIndexS).getSeriesEmbed());
						break;
					}
					}
					break;
					
				// add
				case "add":
					if (comArgs.length < 3) {
						EmbedBuilder addError = new EmbedBuilder()
								.setAuthor("Error")
								.setDescription("There was an error! Make sure your command is formatted correctly.");
						e.getChannel().sendMessage(addError);
					} else {
					switch (comArgs[2]) {
					case "map":
						if (comArgs.length >=4) {
							if (!data.hasSameMap(comArgs[4])) {
								String mapValue = comArgs[4];
								String mapValE = mapValue.replace("_", " ");
								String seriesValue = comArgs[3];
								String seriesValE = seriesValue.replace("_", " ");
								data.addMap(comArgs[4], "map description", "map version", "map length", comArgs[3], "image link", "download link");
								e.getChannel().sendMessage("Map " + mapValE + " created in series " + seriesValE + "!");
							}
						}
						
						break;
					case "series":
						if (comArgs.length >= 3) {
							if (!data.hasSameSeries(comArgs[3])) {
								data.addSeries(comArgs[3], "series author here", "image link here", "download link");
								e.getChannel().sendMessage("Series " + comArgs[3] + " created");
							}
						}
						break;
					}
					}
					break;
					
				// delete
				case "delete":
					if (comArgs.length == 3 || comArgs.length == 2) {
						EmbedBuilder addError = new EmbedBuilder()
								.setAuthor("Error")
								.setDescription("There was an error! Make sure your command is formatted correctly.");
						e.getChannel().sendMessage(addError);
					} else {
					switch (comArgs[2]) {
					case "map":
						try {
							data.deleteMapInFile(comArgs[3]);
							e.getChannel().sendMessage("Map " + comArgs[3] + " deleted.");
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						break;
					case "series":
						try {
							data.deleteSeriesInFile(comArgs[3]);
							e.getChannel().sendMessage("Series " + comArgs[3] + " deleted.");
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						break;
					}
					}
					break;
				// edit
				case "edit":
					if (comArgs.length <= 5 && comArgs.length >= 2) {
						EmbedBuilder addError = new EmbedBuilder()
								.setAuthor("Error")
								.setDescription("There was an error! Make sure your command is formatted correctly.");
						e.getChannel().sendMessage(addError);
					} else {
					switch (comArgs[2]) {
					case "map":
						switch (comArgs[3]) {
						case "name":
							String input = comArgs[5];
							String inputnew = input.replace("_", " ");
							System.out.println("INPUT VALUE: " + comArgs[4]);
							System.out.println("MAP INDEX: " + data.getMapIndexNumber(comArgs[4]));
							data.getMap(data.getMapIndexNumber(comArgs[4])).setMapName(inputnew.replace(" ", "_"));
							try {
								data.updateMapInFile(inputnew.replace(" ", "_"));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getMap(data.getMapIndexNumber(inputnew.replace(" ", "_"))).getEmbed());
							break;
						case "description":
							String input2 = comArgs[5];
							String inputnew2 = input2.replace("_", " ");
							System.out.println(inputnew2);
							int mapIndex2 = data.getMapIndexNumber(comArgs[4]);
							data.getMap(mapIndex2).setMapDescription(inputnew2);
							try {
								data.updateMapInFile(comArgs[4]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getMap(mapIndex2).getEmbed());
							break;
						case "version":
							String input3 = comArgs[5];
							String inputnew3 = input3.replace("'", "");
							int mapIndex3 = data.getMapIndexNumber(comArgs[4]);
							data.getMap(mapIndex3).setMapVersion(inputnew3);
							try {
								data.updateMapInFile(comArgs[4]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getMap(mapIndex3).getEmbed());
							break;
						case "length":
							String input4 = comArgs[5];
							String inputnew4 = input4.replace("'", "");
							int mapIndex4 = data.getMapIndexNumber(comArgs[4]);
							data.getMap(mapIndex4).setMapLength(inputnew4);
							try {
								data.updateMapInFile(comArgs[4]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getMap(mapIndex4).getEmbed());
							break;
						case "series":
							String input5 = comArgs[5];
							String inputnew5 = input5.replace("'", "");
							int mapIndex5 = data.getMapIndexNumber(comArgs[4]);
							data.getMap(mapIndex5).setMapSeries(inputnew5);
							try {
								data.updateMapInFile(comArgs[4]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getMap(mapIndex5).getEmbed());
							break;
						case "image":
							String input6 = comArgs[5];
							String inputnew6 = input6.replace("'", "");
							int mapIndex6 = data.getMapIndexNumber(comArgs[4]);
							data.getMap(mapIndex6).setMapImage(inputnew6);
							try {
								data.updateMapInFile(comArgs[4]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getMap(mapIndex6).getEmbed());
							break;
						case "download":
							String input7 = comArgs[5];
							String inputnew7 = input7.replace("'", "");
							int mapIndex7 = data.getMapIndexNumber(comArgs[4]);
							data.getMap(mapIndex7).setMapDownload(inputnew7);
							try {
								data.updateMapInFile(comArgs[4]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getMap(mapIndex7).getEmbed());
							break;
						}
						break;
					case "series":
						switch (comArgs[3]) {
						case "name":
							String input = comArgs[5];
							String inputnew = input.replace("'", "");
							int seriesIndex = data.getSeriesIndexNumber(comArgs[4]);
							data.getSeries(seriesIndex).setSeriesName(inputnew);
							try {
								data.updateSeriesInFile(comArgs[4]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getSeries(seriesIndex).getSeriesEmbed());
							break;
						case "author":
							String input2 = comArgs[5];
							String inputnew2 = input2.replace("'", "");
							int seriesIndex2 = data.getSeriesIndexNumber(comArgs[4]);
							data.getSeries(seriesIndex2).setSeriesName(inputnew2);
							try {
								data.updateSeriesInFile(comArgs[4]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getSeries(seriesIndex2).getSeriesEmbed());
							break;
						case "image":
							String input3 = comArgs[5];
							String inputnew3 = input3.replace("'", "");
							int seriesIndex3 = data.getSeriesIndexNumber(comArgs[4]);
							data.getSeries(seriesIndex3).setSeriesName(inputnew3);
							try {
								data.updateSeriesInFile(comArgs[4]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getSeries(seriesIndex3).getSeriesEmbed());
							break;
						case "description":
							String input4 = comArgs[5];
							String inputnew4 = input4.replace("'", "");
							int seriesIndex4 = data.getSeriesIndexNumber(comArgs[4]);
							data.getSeries(seriesIndex4).setSeriesName(inputnew4);
							try {
								data.updateSeriesInFile(comArgs[4]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.getChannel().sendMessage(data.getSeries(seriesIndex4).getSeriesEmbed());
							break;
						}
						break;
					}
					}
					break;
				// default
				default:
					break;
				}
			}
		});
	}
}
