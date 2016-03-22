package com.warren.knickknacks.audio.tag;

import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;

import com.warren.knickknacks.Util;

public class MP3AudioTag extends AudioTag {
	
	private MP3File mp3;
	
	@SuppressWarnings("unused")
	private MP3AudioTag() { /* Restrict private constructor */ }
	
	public MP3AudioTag(MP3File mp3) {
		this.mp3 = mp3;
	}

	@Override
	public void save() throws Exception {
		
		ID3v1Tag v1Tag = mp3.getID3v1Tag();
		if(v1Tag != null) {
			if(Util.hasValue(album))
				v1Tag.setAlbum(album);
			if(Util.hasValue(artist))
				v1Tag.setArtist(artist);
			if(Util.hasValue(comment))
				v1Tag.setComment(comment);
			if(Util.hasValue(genre))
				v1Tag.setGenre(genre);
			if(Util.hasValue(title))
				v1Tag.setTitle(title);
			if(Util.hasValue(year))
				v1Tag.setYear(year);
			mp3.setID3v1Tag(v1Tag);
		}
		
		AbstractID3v2Tag v2Tag = mp3.getID3v2Tag();
		if(v2Tag != null) {			
			if(Util.hasValue(album))
				v2Tag.setField(v2Tag.createField(FieldKey.ALBUM, album));
			if(Util.hasValue(artist)) {
				v2Tag.setField(v2Tag.createField(FieldKey.ARTIST, artist));
				v2Tag.setField(v2Tag.createField(FieldKey.ALBUM_ARTIST, artist));
			}
			if(Util.hasValue(comment))
				v2Tag.setField(v2Tag.createField(FieldKey.COMMENT, comment));
			if(Util.hasValue(genre))
				v2Tag.setField(v2Tag.createField(FieldKey.GENRE, genre));
			if(Util.hasValue(title))
				v2Tag.setField(v2Tag.createField(FieldKey.TITLE, title));
			if(Util.hasValue(year))
				v2Tag.setField(v2Tag.createField(FieldKey.YEAR, year));

			mp3.setID3v2Tag(v2Tag);
		}
	}

}
