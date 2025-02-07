import com.example.beatbox.BeatBox
import com.example.beatbox.Sound
import com.example.beatbox.SoundViewModel
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SoundViewModelTest {

    private lateinit var beatBox: BeatBox
    private lateinit var sound: Sound
    private lateinit var subject: SoundViewModel

//    @org.junit.jupiter.api.BeforeEach
    @Before
    fun setUp() {
        beatBox = mock(BeatBox::class.java)
        sound = Sound("assetPath")
        subject = SoundViewModel(beatBox)
        subject.sound = sound
    }

    @Test
    fun exposesSoundNameAsTitle() {
        assertEquals(sound.name, subject.title)
    }

    @Test
    fun callBeatBoxPlayOnButtonClicked() {
        subject.onButtonClicked()
        verify(beatBox).play(sound)
    }
}