package com.github.chipmunk.munkbot;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class MapData {
	
	private String mapName;
	private String mapDescription;
	private String mapVersion;
	private String mapLength;
	private String mapSeries;
	private String mapImage;
	private String mapDownload;
	private EmbedBuilder mapEmbed;
	
	public MapData(String mapName, String mapDescription, String mapVersion, String mapLength, String mapSeries, String mapImage, String mapDownload) {
		this.mapName = mapName;
		this.mapDescription = mapDescription;
		this.mapVersion = mapVersion;
		this.mapLength = mapLength;
		this.mapSeries = mapSeries;
		this.mapImage = mapImage;
		this.mapDownload = mapDownload;
		this.mapEmbed = new EmbedBuilder()
				.setTitle(mapName)
			    .setDescription(mapDescription)
			    .setAuthor(mapSeries)
			    .addInlineField("Map Version", mapVersion)
			    .addInlineField("Map Length", mapLength)
			    .setColor(Color.ORANGE)
			    .setFooter("For link to map, click series name!")
			    .setThumbnail("http://i.imgur.com/XEmaKVo.png");
		if (this.mapImage.startsWith("http")) {
			this.mapEmbed.setImage(this.mapImage);
		}
		if (this.mapDownload.startsWith("http")) {
			this.mapEmbed.setAuthor(this.mapSeries,this.mapDownload,"");
		}
	}
	
	// Set Variable Values
	public void setMapName(String name) {
		this.mapName = name;
		regenerateEmbed();
	}
	public void setMapDescription(String description) {
		this.mapDescription = description;
		regenerateEmbed();
	}
	public void setMapVersion(String version) {
		this.mapVersion = version;
		regenerateEmbed();
	}
	public void setMapLength(String length) {
		this.mapLength = length;
		regenerateEmbed();
	}
	public void setMapSeries(String series) {
		this.mapSeries = series;
		regenerateEmbed();
	}
	public void setMapDownload(String download) {
		this.mapDownload = download;
		regenerateEmbed();
	}
	public void setMapImage(String image) {
		this.mapImage = image;
		regenerateEmbed();
	}
	public void regenerateEmbed() {
		this.mapEmbed = null;
		this.mapEmbed = new EmbedBuilder()
				.setTitle(this.mapName)
				.setDescription(this.mapDescription)
				.setAuthor(this.mapSeries)
				.addInlineField("Map Version", this.mapVersion)
				.addInlineField("Map Length", this.mapLength)
				.setColor(Color.ORANGE)
				//.setImage(this.mapImage)
				.setThumbnail("http://i.imgur.com/XEmaKVo.png")
				.setFooter("For link to map, click series name!");
			if (this.mapImage.startsWith("http")) {
				this.mapEmbed.setImage(this.mapImage);
			}
			if (this.mapDownload.startsWith("http")) {
				this.mapEmbed.setAuthor(this.mapSeries,this.mapDownload,"");
			}
	}
	
	// Get Variable Values
	public String getName() {
		return this.mapName;
	}
	public String getDescription() {
		return this.mapDescription;
	}
	public String getVersion() {
		return this.mapVersion;
	}
	public String getLength() {
		return this.mapLength;
	}
	public String getMapSeries() {
		return this.mapSeries;
	}
	public String getMapDownload() {
		return this.mapDownload;
	}
	public EmbedBuilder getEmbed() {
		return this.mapEmbed;
	}
	public String getMapImage() {
		return this.mapImage;
	}
}
