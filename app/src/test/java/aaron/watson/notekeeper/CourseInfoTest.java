package aaron.watson.notekeeper;

import android.os.Parcel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aaron.watson.notekeeper.course.CourseInfo;
import aaron.watson.notekeeper.data.DataManager;

/**
 * Created by Aaron on 2019-04-09.
 */

public class CourseInfoTest {

    static DataManager sDataManager;

    @BeforeClass
    public static void classSetUp() throws Exception {
        sDataManager = DataManager.getInstance();
    }

    @Before
    public void setUp() throws Exception {
        sDataManager.getNotes().clear();
        sDataManager.initializeCourses();
        sDataManager.initializeExampleNotes();
    }

    @Test
    public void CourseInfoTest() {


        String mockTitle = "Test Course Title";
        String mockID = "TEST_COURSE_ID";

        List<ModuleInfo> modules = new ArrayList<>();
        modules.add(new ModuleInfo("android_async_m01", "Challenges to a responsive user experience", false));
        modules.add(new ModuleInfo("android_async_m02", "Implementing long-running operations as a service", false));
        modules.add(new ModuleInfo("android_async_m03", "Service lifecycle management", true));
        modules.add(new ModuleInfo("android_async_m04", "Interacting with services", true));

        boolean[] completion = new boolean[]{
                false, false, true, true
        };

        boolean[] newComplettionStatus = new boolean[]{
                true, false, true, false
        };

        CourseInfo course = new CourseInfo(mockID, mockTitle, modules);

        int hash = course.hashCode();
        int expectedHash = 1685227602;

        Assert.assertEquals(0, course.describeContents());
        Assert.assertEquals(expectedHash, hash);
        Assert.assertEquals(mockTitle, course.toString());
        Assert.assertEquals(mockID, course.getCourseId());
        Assert.assertTrue(modules.equals(course.getModules()));
        Assert.assertEquals(mockTitle, course.getTitle());

        Assert.assertTrue(Arrays.equals(completion, course.getModulesCompletionStatus()));

        course.setModulesCompletionStatus(newComplettionStatus);

        CourseInfo courseClone = new CourseInfo(mockID, mockTitle, modules);

        Assert.assertTrue(course.equals(courseClone));

        Parcel parcel = MockParcel.obtain();
        course.writeToParcel(parcel, course.describeContents());
        parcel.setDataPosition(0);
        CourseInfo createdFromParcel = CourseInfo.CREATOR.createFromParcel(parcel);
        Assert.assertEquals(createdFromParcel, course);

    }
}
