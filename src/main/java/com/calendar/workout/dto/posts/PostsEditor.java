package com.calendar.workout.dto.posts;


import lombok.Getter;

@Getter
public class PostsEditor {

    private String title;
    private String content;

    public PostsEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static PostsEditor.PostsEditorBuilder builder() {
        return new PostsEditor.PostsEditorBuilder();
    }

    public static class PostsEditorBuilder {
        private String title;
        private String content;

        PostsEditorBuilder() {
        }

        public PostsEditor.PostsEditorBuilder title(final String title) {
            if (title != null) {
                this.title = title;
            }
            return this;
        }

        public PostsEditor.PostsEditorBuilder content(final String content) {
            if (content != null) {
                this.content = content;
            }
            return this;
        }

        public PostsEditor build() {
            return new PostsEditor(this.title, this.content);
        }

        public String toString() {
            return "PostEditor.PostEditorBuilder(title=" + this.title + ", content=" + this.content + ")";
        }
    }
}
