package app.model;

/**
 * Any and All events supported by MODEL meaning app.view could received any of the following events
 */
public enum ModelObservableEvents {
    JOB_CREATED, // id, jobMessenger
    JOB_RUNNING, // id, jobMessenger
    JOB_STOPPED, // id, jobMessenger
    JOB_FINISHED, // id, jobMessenger
    JOB_FILE_PROCESSED, // id, oldName, newName

    INPUT_FOLDER_CHANGED, // old, new
    OUTPUT_FOLDER_CHANGED, // old, new
    OPERATION_CHANGED, // old, new
    SELECTED_SUFFIXES_COLLECTION_CHANGED, // name, collection
    NEW_SUFFIXES_COLLECTION_ADDED, // name, collection
    SUFFIXES_COLLECTION_DELETED, // name, -0

    COUNTING_RELEVANT_FILES_STARTED, // not relevant
    COUNTING_RELEVANT_FILES_FINISHED // count, list<Path>

}