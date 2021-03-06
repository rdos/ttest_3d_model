package org.andresoviedo.app.model3D.demo;

import android.app.Activity;
import android.util.Log;

import org.andresoviedo.android_3d_model_engine.model.Object3DData;
import org.andresoviedo.android_3d_model_engine.model.Object3DDataColor;
import org.andresoviedo.android_3d_model_engine.objects.Cube;
import org.andresoviedo.android_3d_model_engine.services.LoadListener;
import org.andresoviedo.android_3d_model_engine.services.LoaderTask;
import org.andresoviedo.util.android.ContentUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DemoLoaderTask extends LoaderTask {

    /**
     * Build a new progress dialog for loading the data menu_item asynchronously
     *
     * @param parent parent activity
     * @param uri      the URL pointing to the 3d menu_item
     * @param callback listener
     */
    public DemoLoaderTask(Activity parent, URI uri, LoadListener callback) {
        super(parent, uri, callback);
        ContentUtils.provideAssets(parent);
    }

    private static final String TEXT_FMT_OBJECT_3D_DATA_ID = "%sя грань";

    @Override
    protected List<Object3DData> build() throws Exception {

        // notify user
        super.publishProgress("Loading demo...");

        // list of errors found
        final List<Exception> errors = new ArrayList<>();

        try {

//            // test cube made of arrays
//            Object3DData obj10 = Cube.buildCubeV1();
//            obj10.setColor(new float[] { 1f, 0f, 0f, 0.5f });
//            obj10.setLocation(new float[] { -2f, 2f, 0f });
//            obj10.setScale(0.5f, 0.5f, 0.5f);
//            super.onLoad(obj10);

//            // test cube made of wires (I explode it to see the faces better)
//            Object3DData obj11 = Cube.buildCubeV1();
//            obj11.setColor(new float[] { 1f, 1f, 0f, 0.5f });
//            obj11.setLocation(new float[] { 0f, 2f, 0f });
//            Exploder.centerAndScaleAndExplode(obj11, 2.0f, 1.5f);
//            obj11.setId(obj11.getId() + "_exploded");
//            obj11.setScale(0.5f, 0.5f, 0.5f);
//            super.onLoad(obj11);

//
//            // test cube made of wires (I explode it to see the faces better)
//            Object3DData obj12 = Cube.buildCubeV1_with_normals();
//            obj12.setColor(new float[] { 1f, 0f, 1f, 1f });
//            obj12.setLocation(new float[] { 0f, 0f, 0f });
//            obj12.setScale(0.5f, 0.5f, 0.5f);
////            Rescaler.rescale(obj12, -10f);
//            super.onLoad(obj12);

//            float[] ColorCubeTZ = new float[] { 1f, 1f, 0f, 0.25f };
            // more test to check right position
            Object3DData obj111 = Cube.buildCubeV1();
            obj111.setColor(Object3DDataColor.ColorEnum.BLUE);
            obj111.setLocation(new float[] { -0.5f, 0.5f, 0.5f });
            obj111.setScale(0f, 0.5f, 0.5f);
            obj111.setId(String.format(TEXT_FMT_OBJECT_3D_DATA_ID, "1"));
            super.onLoad(obj111);

            // more test to check right position
            Object3DData obj112 = Cube.buildCubeV1();
            obj112.setColor(Object3DDataColor.ColorEnum.GREEN);
            obj112.setLocation(new float[] { -1f, 1f, 0.5f });
            obj112.setScale(0.5f, 0f, 0.5f);
            obj112.setId(String.format(TEXT_FMT_OBJECT_3D_DATA_ID, "2"));
            super.onLoad(obj112);

            // more test to check right position
            Object3DData obj113 = Cube.buildCubeV1();
            obj113.setColor(Object3DDataColor.ColorEnum.BLACK);
            obj113.setLocation(new float[] { -1f, 0f, 0.5f });
            obj113.setScale(0.5f, 0f, 0.5f);
            obj113.setId(String.format(TEXT_FMT_OBJECT_3D_DATA_ID, "3"));
            super.onLoad(obj113);

            // more test to check right position
            Object3DData obj114 = Cube.buildCubeV1();
            obj114.setColor(Object3DDataColor.ColorEnum.WHITE);
            obj114.setLocation(new float[] { -1.5f, 0.5f, 0.5f });
            obj114.setScale(0f, 0.5f, 0.5f);
            obj114.setId(String.format(TEXT_FMT_OBJECT_3D_DATA_ID, "4"));
            super.onLoad(obj114);

//
            // more test to check right position
            Object3DData obj115 = Cube.buildCubeV1();
            obj115.setColor(Object3DDataColor.ColorEnum.YELLOW);
            obj115.setLocation(new float[] { -1f, 0.5f, 0f });
            obj115.setScale(0.5f, 0.5f, 0f);
            obj115.setId(String.format(TEXT_FMT_OBJECT_3D_DATA_ID, "5"));
            super.onLoad(obj115);

            // more test to check right position
            Object3DData obj116 = Cube.buildCubeV1();
            obj116.setColor(Object3DDataColor.ColorEnum.RED);
            obj116.setLocation(new float[] { -1f, 0.5f, 1f });
            obj116.setScale(0.5f, 0.5f, 0f);
            obj116.setId(String.format(TEXT_FMT_OBJECT_3D_DATA_ID, "6"));
            super.onLoad(obj116);


//            // test cube made of indices
//            Object3DData obj20 = Cube.buildCubeV2();
//            obj20.setColor(new float[] { 0f, 1f, 0, 0.25f });
//            obj20.setLocation(new float[] { 2f, 2f, 0f });
//            obj20.setScale(0.5f, 0.5f, 0.5f);
//            super.onLoad(obj20);
//
//            // test cube with texture
//            try {
//                InputStream open = ContentUtils.getInputStream("penguin.bmp");
//                Object3DData obj3 = Cube.buildCubeV3(IOUtils.read(open));
//                open.close();
//                obj3.setColor(new float[] { 1f, 1f, 1f, 1f });
//                obj3.setLocation(new float[] { -2f, -2f, 0f });
//                obj3.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj3);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }
//
//            // test cube with texture & colors
//            try {
//                InputStream open =  ContentUtils.getInputStream("cube.bmp");
//                Object3DData obj4 = Cube.buildCubeV4(IOUtils.read(open));
//                open.close();
//                obj4.setColor(new float[] { 1f, 1f, 1f, 1f });
//                obj4.setLocation(new float[] { 0f, -2f, 0f });
//                obj4.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj4);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }
//
//            // test loading object
//            try {
//                // this has no color array
//                Object3DData obj51 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
//                    @Override
//                    public void onLoad(Object3DData obj53) {
//                        obj53.setLocation(new float[] { -2f, 0f, 0f });
//                        obj53.setColor(new float[] { 1.0f, 1.0f, 0f, 1.0f });
//                        Rescaler.rescale(obj53, 2f);
//                        DemoLoaderTask.this.onLoad(obj53);
//                    }
//                }).load(new URI("assets://assets/models/teapot.obj")).get(0);
//
//                //obj51.setScale(2f,2f,2f);
//                //obj51.setSize(0.5f);
//                //super.onLoad(obj51);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }
//
//            // test loading object with materials
//            try {
//                // this has color array
//                Object3DData obj52 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
//                    @Override
//                    public void onLoad(Object3DData obj53) {
//                        obj53.setLocation(new float[] { 1.5f, -2.5f, -0.5f });
//                        obj53.setColor(new float[] { 0.0f, 1.0f, 1f, 1.0f });
//                        DemoLoaderTask.this.onLoad(obj53);
//                    }
//                }).load(new URI("assets://assets/models/cube.obj")).get(0);
//
//                //obj52.setScale(0.5f, 0.5f, 0.5f);
//                //super.onLoad(obj52);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }
//
//            // test loading object made of polygonal faces
//            try {
//                // this has heterogeneous faces
//                Object3DData obj53 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
//                    @Override
//                    public void onLoad(Object3DData obj53) {
//                        obj53.setLocation(new float[] { 2f, 0f, 0f });
//                        obj53.setColor(new float[] { 1.0f, 1.0f, 1f, 1.0f });
//                        Rescaler.rescale(obj53, 2f);
//                        DemoLoaderTask.this.onLoad(obj53);
//                    }
//                }).load(new URI("assets://assets/models/ToyPlane.obj")).get(0);
//
//                //super.onLoad(obj53);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }
//
//            // test loading object made of polygonal faces
//            try {
//                // this has heterogeneous faces
//                Object3DData obj53 = new ColladaLoader().load(new URI("assets://assets/models/cowboy.dae"), new LoadListenerAdapter(){
//                    @Override
//                    public void onLoad(Object3DData obj53) {
//                        obj53.setLocation(new float[] { 0f, -1f, 1f});
//                        obj53.setColor(new float[] { 1.0f, 1.0f, 1f, 1.0f });
//                        obj53.setRotation(new float[]{-90,0,0});
//                        Rescaler.rescale(obj53, 2f);
//                        DemoLoaderTask.this.onLoad(obj53);
//                    }
//                }).get(0);
//
//                //super.onLoad(obj53);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }
//
//
//            // test loading object without normals
//                    /*try {
//                        Object3DData obj = Object3DBuilder.loadV5(parent, Uri.parse("assets://assets/models/cube4.obj"));
//                        obj.setPosition(new float[] { 0f, 2f, -2f });
//                        obj.setColor(new float[] { 0.3f, 0.52f, 1f, 1.0f });
//                        addObject(obj);
//                    } catch (Exception ex) {
//                        errors.add(ex);
//                    }*/
//
//            // more test to check right position
//            {
//                Object3DData obj111 = Cube.buildCubeV1();
//                obj111.setColor(new float[]{1f, 0f, 0f, 0.25f});
//                obj111.setLocation(new float[]{-1f, -2f, -1f});
//                obj111.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj111);
//
//                // more test to check right position
//                Object3DData obj112 = Cube.buildCubeV1();
//                obj112.setColor(new float[]{1f, 0f, 1f, 0.25f});
//                obj112.setLocation(new float[]{1f, -2f, -1f});
//                obj112.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj112);
//
//            }






            {


//                // more test to check right position
//                Object3DData obj111 = Cube.buildCubeV1();
//                obj111.setColor(new float[] { 0f, 0f, 0f, 0f });
//                obj111.setLocation(new float[] { -1f, -2f, 1f });
//                obj111.setScale(1.5f, 0.5f, 0.5f);
//                super.onLoad(obj111);
//
//
//                // more test to check right position
//                Object3DData obj112 = Cube.buildCubeV1();
//                obj112.setColor(new float[] { 0f, 1f, 1f, 0.25f });
//                obj112.setLocation(new float[] { -1f, -2f, 1f });
//                obj112.setScale(1.5f, 0.5f, 0.5f);
//                super.onLoad(obj112);
            }

        } catch (Exception ex) {
            errors.add(ex);
            if (!errors.isEmpty()) {
                StringBuilder msg = new StringBuilder("There was a problem loading the data");
                for (Exception error : errors) {
                    Log.e("Example", error.getMessage(), error);
                    msg.append("\n").append(error.getMessage());
                }
                throw new Exception(msg.toString());
            }
        }
        return null;
    }

    @Override
    public void onProgress(String progress) {
        super.publishProgress(progress);
    }
}
