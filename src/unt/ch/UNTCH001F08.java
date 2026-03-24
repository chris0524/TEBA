// Updated Code for batchInsert

public void batchInsert(List<Data> dataList) {
    Database db = new Database();
    Connection conn = null;
    List<String> listError = new ArrayList<>();
    try {
        conn = db.getConnection();
        conn.setAutoCommit(false);
        
        for (Data data : dataList) {
            try {
                ut.insert(conn, data);
            } catch (Exception e) {
                listError.add("Error inserting row: " + data + " - " + e.getMessage());
                // Log the detailed error caught from ut._execInsertforDetail()
            }
        }
        
        if (listError.isEmpty()) {
            conn.commit();
            doImportLog();
            // Ensure UNTCH006F01 calBookValueforUNTCH006F01() runs only after successful commit
        } else {
            conn.rollback();
            // Write listError to errorList.txt
            writeErrorListToFile(listError);
        }
    } catch (SQLException e) {
        try { if (conn != null) conn.rollback(); } catch (SQLException rollbackEx) {
            listError.add("Rollback failed: " + rollbackEx.getMessage());
        }
    } finally {
        if (conn != null) {
            try { conn.close(); } catch (SQLException closeEx) { /* Handle closing error */ }
        }
    }
}