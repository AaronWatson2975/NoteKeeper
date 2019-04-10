package aaron.watson.notekeeper;

import android.os.Parcel;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Aaron on 2019-04-09.
 */

public class ModuleInfoTest {

    @Test
    public void ModuleInfoTest() {
        ModuleInfo firstModule = new ModuleInfo("TEST_ID", "Test Title");
        ModuleInfo secondModule = new ModuleInfo("TEST2_ID", "TEST_TITLE2");
        ModuleInfo firstModuleClone = new ModuleInfo("TEST_ID", "Test Title");

        int hash = firstModule.hashCode();
        int expectedHash = -704442392;

        Assert.assertTrue(firstModule.equals(firstModuleClone));
        Assert.assertEquals(0, firstModule.describeContents());
        Assert.assertEquals(expectedHash, hash);
        Assert.assertEquals("Test Title", firstModule.getTitle());

        Parcel parcel = MockParcel.obtain();
        firstModule.writeToParcel(parcel, firstModule.describeContents());
        parcel.setDataPosition(0);
        ModuleInfo createdFromParcel = ModuleInfo.CREATOR.createFromParcel(parcel);
        Assert.assertTrue(createdFromParcel.equals(firstModule));

    }
}
