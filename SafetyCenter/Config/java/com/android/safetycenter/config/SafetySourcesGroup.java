/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.safetycenter.config;

import static com.android.safetycenter.config.SafetySource.SAFETY_SOURCE_TYPE_ISSUE_ONLY;

import static java.util.Objects.requireNonNull;

import android.annotation.IdRes;
import android.annotation.IntDef;
import android.annotation.NonNull;
import android.annotation.Nullable;
import android.content.res.Resources;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Data class used to represent a group of mixed safety sources */
public final class SafetySourcesGroup {

    /**
     * Indicates that the safety sources group should be displayed as a collapsible group with an
     * icon (stateless or stateful) and an optional default summary
     */
    public static final int SAFETY_SOURCES_GROUP_TYPE_COLLAPSIBLE = 0;

    /**
     * Indicates that the safety sources group should be displayed as a rigid group with no icon and
     * no summary
     */
    public static final int SAFETY_SOURCES_GROUP_TYPE_RIGID = 1;

    /**
     * Indicates that the safety sources group should not be displayed.
     */
    public static final int SAFETY_SOURCES_GROUP_TYPE_HIDDEN = 2;

    /**
     * All possible types for a safety sources group.
     *
     * @hide
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(prefix = "SAFETY_SOURCES_GROUP_TYPE_", value = {
            SAFETY_SOURCES_GROUP_TYPE_COLLAPSIBLE,
            SAFETY_SOURCES_GROUP_TYPE_RIGID,
            SAFETY_SOURCES_GROUP_TYPE_HIDDEN
    })
    public @interface SafetySourceGroupType {
    }

    /**
     * Indicates that the safety sources group will not be displayed with any special icon when all
     * the sources contained in it are stateless.
     */
    public static final int STATELESS_ICON_TYPE_NONE = 0;

    /**
     * Indicates that the safety sources group will be displayed with the privacy icon when all the
     * sources contained in it are stateless.
     */
    public static final int STATELESS_ICON_TYPE_PRIVACY = 1;

    /**
     * All possible stateless icon types for a safety sources group.
     *
     * @hide
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(prefix = "STATELESS_ICON_TYPE_", value = {
            STATELESS_ICON_TYPE_NONE,
            STATELESS_ICON_TYPE_PRIVACY
    })
    public @interface StatelessIconType {
    }

    @NonNull
    private final String mId;
    @IdRes
    private final int mTitleResId;
    @IdRes
    private final int mSummaryResId;
    @StatelessIconType
    private final int mStatelessIconType;
    @NonNull
    private final List<SafetySource> mSafetySources;

    private SafetySourcesGroup(@NonNull String id, @IdRes int titleResId, @IdRes int summaryResId,
            @StatelessIconType int statelessIconType, @NonNull List<SafetySource> safetySources) {
        mId = id;
        mTitleResId = titleResId;
        mSummaryResId = summaryResId;
        mStatelessIconType = statelessIconType;
        mSafetySources = safetySources;
    }

    /** Returns the type of this safety sources group. */
    @SafetySourceGroupType
    public int getType() {
        if (mTitleResId == Resources.ID_NULL) {
            return SAFETY_SOURCES_GROUP_TYPE_HIDDEN;
        }
        if (mSummaryResId != Resources.ID_NULL || mStatelessIconType != STATELESS_ICON_TYPE_NONE) {
            return SAFETY_SOURCES_GROUP_TYPE_COLLAPSIBLE;
        }
        return SAFETY_SOURCES_GROUP_TYPE_RIGID;
    }

    /** Returns the id of this safety sources group. */
    @NonNull
    public String getId() {
        return mId;
    }

    /** Returns the resource id of the title of this safety sources group. */
    @IdRes
    public int getTitleResId() {
        return mTitleResId;
    }

    /** Returns the resource id of the summary of this safety sources group. */
    @IdRes
    public int getSummaryResId() {
        return mSummaryResId;
    }

    /** Returns the stateless icon type of this safety sources group. */
    @StatelessIconType
    public int getStatelessIconType() {
        return mStatelessIconType;
    }

    /** Returns the list of safety sources in this safety sources group. */
    @NonNull
    public List<SafetySource> getSafetySources() {
        return mSafetySources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SafetySourcesGroup)) return false;
        SafetySourcesGroup that = (SafetySourcesGroup) o;
        return Objects.equals(mId, that.mId)
                && mTitleResId == that.mTitleResId
                && mSummaryResId == that.mSummaryResId
                && mStatelessIconType == that.mStatelessIconType
                && Objects.equals(mSafetySources, that.mSafetySources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitleResId, mSummaryResId, mStatelessIconType, mSafetySources);
    }

    @Override
    public String toString() {
        return "SafetyCenterConfig{"
                + "mId='" + mId + '\''
                + ", mTitleResId=" + mTitleResId
                + ", mSummaryResId=" + mSummaryResId
                + ", mStatelessIconType=" + mStatelessIconType
                + ", mSafetySources=" + mSafetySources
                + '}';
    }

    /** Builder class for {@link SafetySourcesGroup}. */
    public static final class Builder {
        @Nullable
        private String mId;
        @Nullable
        @IdRes
        private Integer mTitleResId;
        @Nullable
        @IdRes
        private Integer mSummaryResId;
        @Nullable
        @StatelessIconType
        private Integer mStatelessIconType;
        @NonNull
        private final List<SafetySource> mSafetySources = new ArrayList<>();

        /** Creates a {@link Builder} for a {@link SafetySourcesGroup}. */
        public Builder() {
        }

        /** Sets the id of this safety sources group. */
        @NonNull
        public Builder setId(@Nullable String id) {
            mId = id;
            return this;
        }

        /** Sets the resource id of the title of this safety sources group. */
        @NonNull
        public Builder setTitleResId(@IdRes int titleResId) {
            mTitleResId = titleResId;
            return this;
        }

        /** Sets the resource id of the summary of this safety sources group. */
        @NonNull
        public Builder setSummaryResId(@IdRes int summaryResId) {
            mSummaryResId = summaryResId;
            return this;
        }

        /** Sets the stateless icon type of this safety sources group. */
        @NonNull
        public Builder setStatelessIconType(@StatelessIconType int statelessIconType) {
            mStatelessIconType = statelessIconType;
            return this;
        }

        /** Adds a safety source to this safety sources group. */
        @NonNull
        public Builder addSafetySource(@NonNull SafetySource safetySource) {
            mSafetySources.add(requireNonNull(safetySource));
            return this;
        }

        /** Creates the {@link SafetySourcesGroup} defined by this {@link Builder}. */
        @NonNull
        public SafetySourcesGroup build() {
            BuilderUtils.validateAttribute(mId, "id", true, false);
            if (mSafetySources.isEmpty()) {
                throw new IllegalStateException("Safety sources group empty");
            }
            boolean titleRequired = false;
            int safetySourcesSize = mSafetySources.size();
            for (int i = 0; i < safetySourcesSize; i++) {
                int type = mSafetySources.get(i).getType();
                if (type != SAFETY_SOURCE_TYPE_ISSUE_ONLY) {
                    titleRequired = true;
                    break;
                }
            }
            int titleResId = BuilderUtils.validateResId(mTitleResId, "title", titleRequired, false);
            int summaryResId = BuilderUtils.validateResId(mSummaryResId, "summary", false, false);
            int statelessIconType = BuilderUtils.validateIntDef(mStatelessIconType,
                    "statelessIconType", false, false, STATELESS_ICON_TYPE_NONE,
                    STATELESS_ICON_TYPE_NONE, STATELESS_ICON_TYPE_PRIVACY);
            return new SafetySourcesGroup(mId, titleResId, summaryResId, statelessIconType,
                    Collections.unmodifiableList(mSafetySources));
        }
    }

}