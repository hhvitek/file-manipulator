package app.model.file_operations;

public enum FileOperationEnum {
    COPY {
        @Override
        public IFileOperation getFileOperationInstance() {
            return Constants.COPY_OPERATION;
        }
    },
    RENAME {
        @Override
        public IFileOperation getFileOperationInstance() {
            return Constants.RENAME_OPERATION;
        }
    },
    MOVE {
        @Override
        public IFileOperation getFileOperationInstance() {
            return Constants.MOVE_OPERATION;
        }
    },
    DELETE {
        @Override
        public IFileOperation getFileOperationInstance() {
            return Constants.DELETE_OPERATION;
        }
    };

    public abstract IFileOperation getFileOperationInstance();

    private static final class Constants {
        public static final IFileOperation COPY_OPERATION = new CopyFileOperation();
        public static final IFileOperation RENAME_OPERATION = new RenameFileOperation();
        public static final IFileOperation MOVE_OPERATION = new MoveFileOperation();
        public static final IFileOperation DELETE_OPERATION = new DeleteFileOperation();
    }
}