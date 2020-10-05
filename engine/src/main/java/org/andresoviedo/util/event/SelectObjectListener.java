package org.andresoviedo.util.event;

import org.andresoviedo.android_3d_model_engine.model.Object3DData;

import java.util.EventObject;

public interface SelectObjectListener {
    boolean onSelectObject(Object3DData object3DData);
}
