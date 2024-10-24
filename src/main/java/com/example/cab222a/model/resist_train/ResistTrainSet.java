package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

/**
 * Data class for resistance training sets. Used in Data Access Objects (DAOs)
 * and Model View Controllers (MVCs) for this application.
 */
public class ResistTrainSet extends NamedObject {
    private int exerciseId;
    private int weight;
    private int reps;
    private int rest;
    private int repsInReserve;

    /**
     * Constructor with ID (for when entries have been assigned an ID in the database).
     * @param id The ID of the set.
     * @param name The name of the set.
     * @param exerciseId The ID of the exercise which this set belongs to.
     * @param weight The weight for this set.
     * @param reps The number of reps performed for this set.
     * @param rest The number of seconds of rest after this set.
     * @param repsInReserve The number of reps that could have been performed just before the set concluded.
     */
    public ResistTrainSet(int id, String name, int exerciseId, int weight, int reps, int rest, int repsInReserve) {
        super(id, name);
        this.exerciseId = exerciseId;
        this.weight = weight;
        this.reps = reps;
        this.rest = rest;
        this.repsInReserve = repsInReserve;
    }

    /**
     * Constructor with ID (for when entries have not been assigned an ID in the database yet).
     * @param name The name of the set.
     * @param exerciseId The ID of the exercise which this set belongs to.
     * @param weight The weight for this set.
     * @param reps The number of reps performed for this set.
     * @param rest The number of seconds of rest after this set.
     * @param repsInReserve The number of reps that could have been performed just before the set concluded.
     */
    public ResistTrainSet(String name, int exerciseId, int weight, int reps, int rest, int repsInReserve) {
        super(name);
        this.exerciseId = exerciseId;
        this.weight = weight;
        this.reps = reps;
        this.rest = rest;
        this.repsInReserve = repsInReserve;

    }

    /**
     *
     * @return The ID of the exercise which this set belongs to.
     */
    public int getExerciseId() {
        return exerciseId;
    }

    /**
     *
     * @param exerciseId The ID of the exercise which this set belongs to.
     */
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     *
     * @return The weight for this set.
     */
    public int getWeight() {
        return weight;
    }

    /**
     *
     * @param weight The weight for this set.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     *
     * @return The number of reps performed for this set.
     */
    public int getReps() {
        return reps;
    }

    /**
     *
     * @param reps The number of reps performed for this set.
     */
    public void setReps(int reps) {
        this.reps = reps;
    }

    /**
     *
     * @return The number of seconds of rest after this set.
     */
    public int getRest() {
        return rest;
    }

    /**
     *
     * @param rest The number of seconds of rest after this set.
     */
    public void setRest(int rest) {
        this.rest = rest;
    }

    /**
     *
     * @return The number of reps that could have been performed just before the set concluded.
     */
    public int getRepsInReserve() {
        return repsInReserve;
    }

    /**
     *
     * @param repsInReserve The number of reps that could have been performed just before the set concluded.
     */
    public void setRepsInReserve(int repsInReserve) {
        this.repsInReserve = repsInReserve;
    }

    /**
     * Concatenates exercise information into a human-readable format.
     * @return The String containing the said user information.
     */
    @Override
    public String toString() {
        return "ResistTrainSet{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", exerciseId=" + getExerciseId() +
                ", weight=" + getWeight() +
                ", reps=" + getReps() +
                ", rest=" + getRest() +
                ", repsInReserve=" + getRepsInReserve() +
                '}';
    }
}
