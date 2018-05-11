Class12_IntegerStorageDemo
內部儲存

 1.內存可以用來儲存app的私有資料，當app移除時，存在內存的檔案也會一起移除
 2.寫入檔案
     呼叫 Context.openFileOutput (檔名,模式)，會回傳
     FileOutputStream 物件，再使用此物件寫入檔案
 3.讀取檔案
     呼叫 Context.openFileInput (檔名)，會回傳
     FileInputStream 物件，再使用此物件讀取檔案
 4.常用的方法
     (1). getFilesDir()：會回傳 File 物件，表示內存的路徑 return absolute path
     (2). getDir()：建立或開啟一個在內存路徑下的資料夾，回傳 File 物件
     (3). fileList()：列出內存路徑下的所有檔案及資料夾，回傳 String[] 物件
     (4). deleteFile()：刪除一個在內存路徑下的檔案，回傳 boolean

