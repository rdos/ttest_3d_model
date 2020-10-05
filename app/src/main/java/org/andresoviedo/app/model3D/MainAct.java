package org.andresoviedo.app.model3D;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.andresoviedo.android_3d_model_engine.camera.CameraController;
import org.andresoviedo.android_3d_model_engine.collision.CollisionController;
import org.andresoviedo.android_3d_model_engine.controller.TouchController;
import org.andresoviedo.android_3d_model_engine.model.Object3DData;
import org.andresoviedo.android_3d_model_engine.services.LoaderTask;
import org.andresoviedo.android_3d_model_engine.services.SceneLoader;
import org.andresoviedo.android_3d_model_engine.view.ModelRenderer;
import org.andresoviedo.android_3d_model_engine.view.ModelSurfaceView;
import org.andresoviedo.app.model3D.demo.DemoLoaderTask;
import org.andresoviedo.app.model3D.view.ModelViewerGUI;
import org.andresoviedo.dddmodel2.R;
import org.andresoviedo.util.android.AndroidURLStreamHandlerFactory;
import org.andresoviedo.util.event.EventListener;
import org.andresoviedo.util.event.SelectObjectListener;

import java.net.URI;
import java.net.URL;
import java.util.EventObject;


public class MainAct extends Activity implements EventListener, SelectObjectListener {
    private ModelSurfaceView mGLView;
    private LinearLayout llMainAct;
    private ListView lvMainAct;
    private SelectObject3DAdapter mSelectObject3DAdapter;

    private static final int REQUEST_CODE_LOAD_TEXTURE = 1000;
    private static final int FULLSCREEN_DELAY = 10000;

    // Custom handler: org/andresoviedo/util/android/assets/Handler.class
    static {
        System.setProperty("java.protocol.handler.pkgs", "org.andresoviedo.util.android");
        URL.setURLStreamHandlerFactory(new AndroidURLStreamHandlerFactory());
    }

    /**
     *
     * Type of menu_item if file name has no extension (provided though content provider)
     */
    private int paramType;
    /**
     * The file to load. Passed as input parameter
     */
    private URI paramUri;
    /**
     * Enter into Android Immersive mode so the renderer is full screen or not
     */
    private boolean immersiveMode;
    /**
     * Background GL clear color. Default is light gray
     */
    private float[] backgroundColor = new float[]{0.8f, 0.8f, 0.8f, 0.8f};

    private TouchController touchController;
    private SceneLoader mSceneLoader;
    private ModelViewerGUI gui;
    private CollisionController collisionController;


    private Handler handler;
    //TODO: R!!!
    private CameraController mCameraController;
    private Drawable mLastMenuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ModelActivity", "Loading activity...");
        super.onCreate(savedInstanceState);

        // Try to get input parameters
        Bundle b = getIntent().getExtras();
        if (b != null) {
            try {
                if (b.getString("uri") != null) {
                    this.paramUri = new URI(b.getString("uri"));
                    Log.i("ModelActivity", "Params: uri '" + paramUri + "'");
                }
                this.paramType = b.getString("type") != null ? Integer.parseInt(b.getString("type")) : -1;
                this.immersiveMode = "true".equalsIgnoreCase(b.getString("immersiveMode"));

                if (b.getString("backgroundColor") != null) {
                    String[] backgroundColors = b.getString("backgroundColor").split(" ");
                    backgroundColor[0] = Float.parseFloat(backgroundColors[0]);
                    backgroundColor[1] = Float.parseFloat(backgroundColors[1]);
                    backgroundColor[2] = Float.parseFloat(backgroundColors[2]);
                    backgroundColor[3] = Float.parseFloat(backgroundColors[3]);
                }
            } catch (Exception ex) {
                Log.e("ModelActivity", "Error parsing activity parameters: " + ex.getMessage(), ex);
            }

        }

        handler = new Handler(getMainLooper());

        // Create our 3D scenario
        Log.i("ModelActivity", "Loading Scene...");
        mSceneLoader = new SceneLoader(this, paramUri, paramType, mGLView, this::onSelectObject);
        if (paramUri == null) {
            final LoaderTask task = new DemoLoaderTask(this, null, mSceneLoader);
            task.execute();
        }

        try {
            Log.i("ModelActivity", "Loading GLSurfaceView...");
            setContentView(R.layout.act_main);

            llMainAct = findViewById(R.id.ll_main_act);
            llMainAct.setVisibility(View.GONE);

            lvMainAct = findViewById(R.id.lv_main_act);

            mGLView = findViewById(R.id.id_glsurface_view);
            mGLView.setInitTODO(this, backgroundColor, this.mSceneLoader);
//            gLView = new ModelSurfaceView(this, backgroundColor, this.scene);
            mGLView.addListener(this);

            mSceneLoader.setView(mGLView);
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading OpenGL view:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            Log.i("ModelActivity", "Loading TouchController...");
            touchController = new TouchController(this);
            touchController.addListener(this);
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading TouchController:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            Log.i("ModelActivity", "Loading CollisionController...");
            collisionController = new CollisionController(mGLView, mSceneLoader);
            collisionController.addListener(mSceneLoader);
            touchController.addListener(collisionController);
            touchController.addListener(mSceneLoader);
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading CollisionController\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            Log.i("ModelActivity", "Loading CameraController...");
            mCameraController = new CameraController(mSceneLoader.getCamera());
            mGLView.getModelRenderer().addListener(mCameraController);
            touchController.addListener(mCameraController);
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading CameraController" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            // TODO: finish UI implementation
            Log.i("ModelActivity", "Loading GUI...");
            gui = new ModelViewerGUI(mGLView, mSceneLoader);
            touchController.addListener(gui);
            mGLView.addListener(gui);
            mSceneLoader.addGUIObject(gui);
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading GUI" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Show the Up button in the action bar.
        setupActionBar();

        setupOnSystemVisibilityChangeListener();

        // load menu_item
        mSceneLoader.init();
        mSceneLoader.setLightOff();

        mSelectObject3DAdapter = new SelectObject3DAdapter(mSceneLoader);
        lvMainAct.setAdapter(mSelectObject3DAdapter);
        Log.i("ModelActivity", "Finished loading");
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        // }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setupOnSystemVisibilityChangeListener() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            // Note that system bars will only be "visible" if none of the
            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                // The system bars are visible. Make any desired
                hideSystemUIDelayed();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUIDelayed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.model_toggle_wireframe:
                mSceneLoader.toggleWireframe();
                break;
            case R.id.menu_item_enable_select:
                setSelectObjectMode(item);
//                mSceneLoader.toggleBoundingBox();
                break;
//            case R.id.model_toggle_textures:
//                mScene.toggleTextures();
//                break;
//            case R.id.model_toggle_animation:
//                mScene.toggleAnimation();
//                break;
//            case R.id.model_toggle_smooth:
//                mScene.toggleSmooth();
//                break;
//            case R.id.model_toggle_collision:
//                mScene.toggleCollision();
//                break;
//            case R.id.model_toggle_lights:
//                mScene.toggleLighting();
//                break;


//            case R.id.model_toggle_stereoscopic:
//                mScene.toggleStereoscopic();
//                break;


            //TODO: R??? Toggle X-Ray :)
//            case R.id.model_toggle_blending:
//                mScene.toggleBlending();
//                break;
//            case R.id.model_toggle_immersive:
//                toggleImmersive();
//                break;
//            case R.id.model_load_texture:
//                Intent target = ContentUtils.createGetContentIntent("image/*");
//                Intent intent = Intent.createChooser(target, "Select a file");
//                try {
//                    startActivityForResult(intent, REQUEST_CODE_LOAD_TEXTURE);
//                } catch (ActivityNotFoundException e) {
//                    // The reason for the existence of aFileChooser
//                }
//                break;
        }

        hideSystemUIDelayed();
        return super.onOptionsItemSelected(item);
    }

    private void setSelectObjectMode(MenuItem item) {
        if (item.getIcon() == null) {
            item.setIcon(mLastMenuIcon);
            mSceneLoader.setSelectedObjectModeOff();
            llMainAct.setVisibility(View.GONE);
//            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        } else {
            mLastMenuIcon =  item.getIcon();
            item.setIcon(null);
            mSceneLoader.setSelectedObjectMode();
        }
    }

    //TODO: R??? full screen
    private void toggleImmersive() {
        this.immersiveMode = !this.immersiveMode;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        if (this.immersiveMode) {
            hideSystemUI();
        } else {
            showSystemUI();
        }
        Toast.makeText(this, "Fullscreen " + this.immersiveMode, Toast.LENGTH_SHORT).show();
    }

    private void hideSystemUIDelayed() {
        if (!this.immersiveMode) {
            return;
        }
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(this::hideSystemUI, FULLSCREEN_DELAY);

    }

    private void hideSystemUI() {
        if (!this.immersiveMode) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            hideSystemUIKitKat();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            hideSystemUIJellyBean();
        }
    }

    // This snippet hides the system bars.
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void hideSystemUIKitKat() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void hideSystemUIJellyBean() {
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showSystemUI() {
        handler.removeCallbacksAndMessages(null);
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }


    @Override
    public boolean onEvent(EventObject event) {
        if (event instanceof ModelRenderer.ViewEvent) {
            ModelRenderer.ViewEvent viewEvent = (ModelRenderer.ViewEvent) event;
            if (viewEvent.getCode() == ModelRenderer.ViewEvent.Code.SURFACE_CHANGED) {
                touchController.setSize(viewEvent.getWidth(), viewEvent.getHeight());
                mGLView.setTouchController(touchController);

                // process event in GUI
                if (gui != null) {
                    gui.setSize(viewEvent.getWidth(), viewEvent.getHeight());
                    gui.setVisible(true);
                }
            }
        }
        return true;
    }

    @Override
    public boolean onSelectObject(Object3DData object3DData) {
        if (object3DData == null) {
            llMainAct.setVisibility(View.GONE);
            return false;
        }
        llMainAct.setVisibility(View.VISIBLE);
        mSelectObject3DAdapter.notifyDataSetChanged();
        return false;
    }

    private class SelectObject3DAdapter extends BaseAdapter {
        final private SceneLoader mSceneLoader;
        public SelectObject3DAdapter(SceneLoader sceneLoader) {
            mSceneLoader = sceneLoader;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return mSceneLoader.getSelectedObject();
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            // используем созданные, но не используемые view
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.act_main_lv_item, parent, false);
            }

            TextView tvId = view.findViewById(R.id.tv_act_main_lv_item_id);
            tvId.setText(mSceneLoader.getSelectedObject().getId());

            TextView tvColor = view.findViewById(R.id.tv_act_main_lv_item_color);
            tvColor.setText(mSceneLoader.getSelectedObject().getColorName());
//            Product p = getProduct(position);
//
//            // заполняем View в пункте списка данными из товаров: наименование, цена
//            // и картинка
//            ((TextView) view.findViewById(R.id.tvDescr)).setText(p.name);
//            ((TextView) view.findViewById(R.id.tvPrice)).setText(p.price + "");
//            ((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);
//
//            CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
//            // присваиваем чекбоксу обработчик
//            cbBuy.setOnCheckedChangeListener(myCheckChangeList);
//            // пишем позицию
//            cbBuy.setTag(position);
//            // заполняем данными из товаров: в корзине или нет
//            cbBuy.setChecked(p.box);
            return view;
        }
    }
}

/**
 TODO:R!!!
 private void loadModel() {
 ContentUtils.showListDialog(this, "File Provider", new String[]{"Samples", "Repository",
 "File Explorer", "Android Explorer"}, (DialogInterface dialog, int which) -> {
 if (which == 0) {
 loadModelFromAssets();
 } else if (which == 1) {
 loadModelFromRepository();
 } else if (which == 2) {
 loadModelFromSdCard();
 } else {
 loadModelFromContentProvider();
 }
 });

 }
 */