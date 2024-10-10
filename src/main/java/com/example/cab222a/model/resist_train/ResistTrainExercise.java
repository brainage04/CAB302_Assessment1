package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

/**
 * Data class for resistance training exercises. Used in Data Access Objects (DAOs)
 * and Model View Controllers (MVCs) for this application.
 */
public class ResistTrainExercise extends NamedObject {
    private int sessionId;
    private int exerciseInfoId;

    /**
     * Constructor with ID (for when entries have been assigned an ID in the database).
     * @param id The ID of the exercise.
     * @param name The name of the exercise.
     * @param sessionId The ID of the session which this exercise belongs to.
     * @param exerciseInfoId The ID of the exercise information which this exercise is associated with.
     */
    public ResistTrainExercise(int id, String name, int sessionId, int exerciseInfoId) {
        super(id, name);
        this.sessionId = sessionId;
        this.exerciseInfoId = exerciseInfoId;
    }

    /**
     * Constructor with ID (for when entries have not been assigned an ID in the database yet).
     * @param name The name of the exercise.
     * @param sessionId The ID of the session which this exercise belongs to.
     * @param exerciseInfoId The ID of the exercise information which this exercise is associated with.
     */
    public ResistTrainExercise(String name, int sessionId, int exerciseInfoId) {
        super(name);
        this.sessionId = sessionId;
        this.exerciseInfoId = exerciseInfoId;
    }

    /**
     *
     * @return The ID of the session which this exercise belongs to.
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     *
     * @param sessionId The ID of the session which this exercise belongs to.
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    /**
     *
     * @return The ID of the exercise information which this exercise is associated with.
     */
    public int getExerciseInfoId() {
        return exerciseInfoId;
    }

    /**
     *
     * @param exerciseInfoId The ID of the exercise information which this exercise is associated with.
     */
    public void setExerciseInfoId(int exerciseInfoId) {
        this.exerciseInfoId = exerciseInfoId;
    }

    /**
     * Concatenates exercise information into a human-readable format.
     * @return The String containing the said user information.
     */
    @Override
    public String toString() {
        return "ResistTrainExercise{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", sessionId=" + getSessionId() +
                ", exerciseInfoId=" + getExerciseInfoId() +
                '}';
    }
}
