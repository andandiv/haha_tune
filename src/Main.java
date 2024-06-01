import javax.naming.directory.InvalidAttributeIdentifierException;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Main {

    public static Clip clip;

    public static void main(String[] args) throws LineUnavailableException, IOException {
        System.out.println("Hello world!");

        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : mixInfos) {
            System.out.println(info.getName() + " " + info.getDescription());
        }

        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);

        Mixer mixer = null;
        for (Mixer.Info info : mixInfos) {
            Mixer m = AudioSystem.getMixer(info);
            if (m.isLineSupported(dataInfo)) {
                mixer = m;
                break;
            }
        }

        if (mixer == null) {
            throw new RuntimeException("No suitable mixer found");
        }

        try {
            clip = (Clip) mixer.getLine(dataInfo);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        AudioInputStream audioStream1;
        AudioInputStream audioStream2;
        AudioInputStream audioStream3;
        AudioInputStream audioStream4;

        try {
            URL soundURL = Main.class.getClassLoader().getResource("file_example_WAV_1MG.wav");
            URL soundAsharp = Main.class.getClassLoader().getResource("piano-bb_A#_major.wav");
            URL soundDsharp = Main.class.getClassLoader().getResource("piano-eb_D#_major.wav");
            URL soundCsharp = Main.class.getClassLoader().getResource("piano-c_C#_major.wav");
            URL soundFsharp = Main.class.getClassLoader().getResource("piano-f_F#_major.wav");

            audioStream1 = AudioSystem.getAudioInputStream(soundAsharp);
            audioStream2 = AudioSystem.getAudioInputStream(soundCsharp);
            audioStream3 = AudioSystem.getAudioInputStream(soundFsharp);
            audioStream4 = AudioSystem.getAudioInputStream(soundDsharp);

//            clip.open(audioStream1);

        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }


        clip.open(audioStream1);
        clip.start();

        halt();

        clip.close();
        clip.open(audioStream2);
        clip.start();

        halt();

        clip.close();
        clip.open(audioStream3);
        clip.start();

        halt();

        clip.close();
        clip.open(audioStream3);
        clip.start();

        halt();

        clip.close();
        clip.open(audioStream4);
        clip.start();

        halt();


    }

    private static void halt() {

        do {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (clip.isActive());

    }
}