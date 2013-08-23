EJMP (Easy Java Media Player)
===============================

Java上で様々な形式のサウンドファイルを再生できるPure Javaのサウンドプレイヤーライブラリです。
一部形式の再生には別途外部のライブラリが必要になりますが、全ての形式を同一のインタフェースで簡単に再生できます。

使い方
---------------------------------

  SoundPlayer player = new SoundPlayer();
  MediaLocation location = new MediaLocation(new File("test.aiff"));
  player.setMedia(location);
  player.play();


現在の対応形式
---------------------------------

wav, mp3, ogg, flac, aiff

mp3, ogg, flacは別途ライブラリを入手し、クラスパス上に置く必要があります。  
詳しくはrequirements.txtを参照してください。


ライセンス
---------------------------------

MPL2.0またはLGPLとします。

