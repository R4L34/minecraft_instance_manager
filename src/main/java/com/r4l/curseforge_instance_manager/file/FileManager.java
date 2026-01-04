package com.r4l.curseforge_instance_manager.file;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {
	
	public static List<Path> getFolders(Path directory){
        try (Stream<Path> stream = Files.list(directory)) {
            return stream.filter(Files::isDirectory).collect(Collectors.toList());
        }catch (IOException e) {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	public static void deleteRecursive(Path root) throws IOException {
        if (!Files.exists(root)) return;

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                // delete directory after its contents
                Files.deleteIfExists(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
	
	public static void copyRecursive(Path source, Path target) throws IOException {
        if (!Files.exists(source) || !Files.isDirectory(source)) {
            throw new IllegalArgumentException("Source folder does not exist: " + source);
        }

        Files.createDirectories(target);

        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path relative = source.relativize(dir);
                Path targetDir = target.resolve(relative);
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relative = source.relativize(file);
                Path targetFile = target.resolve(relative);

                Files.copy(
                        file,
                        targetFile,
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                        java.nio.file.StandardCopyOption.COPY_ATTRIBUTES
                );
                return FileVisitResult.CONTINUE;
            }
        });
    }
	
	
	public static void copyFolder(Path toReplace, Path replacement) {

		    try {
		        if (Files.exists(toReplace)) {
		            deleteRecursive(toReplace);
		        }

		        copyRecursive(replacement, toReplace);

		        System.out.println("Files replaced successfully.");
		    } catch (IOException e) {
		        throw new RuntimeException("Failed to replace Files", e);
		    }
	}
	
	
	private static String joinAbsRel(Path rootAbs, String childRel) {
	    String root = rootAbs.toString();
	    if (!root.endsWith(FileSystems.getDefault().getSeparator())) {
	        root += FileSystems.getDefault().getSeparator();
	    }
	    return root + childRel;
	}

	/**
	 * Overload: delete ONLY folders/files listed (relative to absolute root).
	 */
	public static void deleteRecursive(Path rootAbs, List<String> folders, List<String> files) throws IOException {
	    if (rootAbs == null) throw new IllegalArgumentException("root is null");
	    if (!Files.exists(rootAbs) || !Files.isDirectory(rootAbs)) return;

	    // delete selected folders (recursively)
	    if (folders != null) {
	        for (String folderName : folders) {
	            if (folderName == null || folderName.isEmpty()) continue;

	            Path folderPath = Paths.get(joinAbsRel(rootAbs, folderName));
	            if (Files.exists(folderPath)) {
	                deleteRecursive(folderPath); // calls your original full delete for that folder only
	            }
	        }
	    }

	    // delete selected files
	    if (files != null) {
	        for (String fileName : files) {
	            if (fileName == null || fileName.isEmpty()) continue;

	            Path filePath = Paths.get(joinAbsRel(rootAbs, fileName));

	            if (Files.exists(filePath) && Files.isDirectory(filePath)) {
	                // if a directory name accidentally lands in "files", delete it recursively
	                deleteRecursive(filePath);
	            } else {
	                Files.deleteIfExists(filePath);
	            }
	        }
	    }
	}

	/**
	 * Overload: copy ONLY folders/files listed from sourceAbs -> targetAbs.
	 * For each listed item: delete target item (if exists) then copy from source (if exists).
	 */
	public static void copyRecursive(Path sourceAbs, Path targetAbs, List<String> folders, List<String> files) throws IOException {
	    if (sourceAbs == null || targetAbs == null) throw new IllegalArgumentException("source/target is null");
	    if (!Files.exists(sourceAbs) || !Files.isDirectory(sourceAbs)) {
	        throw new IllegalArgumentException("Source folder does not exist: " + sourceAbs);
	    }

	    Files.createDirectories(targetAbs);

	    // copy selected folders
	    if (folders != null) {
	        for (String folderName : folders) {
	            if (folderName == null || folderName.isEmpty()) continue;

	            Path srcDir = Paths.get(joinAbsRel(sourceAbs, folderName));
	            Path dstDir = Paths.get(joinAbsRel(targetAbs, folderName));

	            if (!Files.exists(srcDir) || !Files.isDirectory(srcDir)) continue;

	            if (Files.exists(dstDir)) {
	                deleteRecursive(dstDir); // delete only that target folder
	            }
	            copyRecursive(srcDir, dstDir); // calls your original full copy for that folder only
	        }
	    }

	    // copy selected files
	    if (files != null) {
	        for (String fileName : files) {
	            if (fileName == null || fileName.isEmpty()) continue;

	            Path src = Paths.get(joinAbsRel(sourceAbs, fileName));
	            Path dst = Paths.get(joinAbsRel(targetAbs, fileName));

	            if (!Files.exists(src)) continue;

	            if (Files.isDirectory(src)) {
	                // if a directory name accidentally lands in "files", treat it like a folder copy
	                if (Files.exists(dst)) deleteRecursive(dst);
	                copyRecursive(src, dst);
	                continue;
	            }

	            if (dst.getParent() != null) {
	                Files.createDirectories(dst.getParent());
	            }

	            Files.copy(src, dst,
	                    StandardCopyOption.REPLACE_EXISTING,
	                    StandardCopyOption.COPY_ATTRIBUTES);
	        }
	    }
	}
	
	
	
	
	public static void copyFolder(Path toReplace, Path replacement, List<String> folders, List<String> files) {

	    try {
	        // delete ONLY listed items inside toReplace
	        deleteRecursive(toReplace, folders, files);

	        // copy ONLY listed items from replacement -> toReplace
	        copyRecursive(replacement, toReplace, folders, files);

	        System.out.println("Selected files/folders replaced successfully.");
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to replace selected files", e);
	    }
	}

}
