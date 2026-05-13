#!/bin/bash
javac com/mbahng/lox/*.java

# 2. Define colors for pretty output                                                                                                                                                                      
GREEN='\033[0;32m'                                                                                                                                                                                        
RED='\033[0;31m'                                                                                                                                                                                          
NC='\033[0m' # No Color                                                                                                                                                                                   
                                                                                                                                                                                                          
# 3. Loop through all .lox files in the integration folder                                                                                                                                                
for lox_file in tests/integration/*.lox; do                                                                                                                                                               
    # Get the base name (e.g., "arithmetic" from "tests/integration/arithmetic.lox")                                                                                                                      
    base_name="${lox_file%.lox}"                                                                                                                                                                          
    expected_output="$base_name.out"                                                                                                                                                                      
                                                                                                                                                                                                          
    echo -n "Testing $lox_file... "                                                                                                                                                                       
                                                                                                                                                                                                          
    # 4. Run Lox and capture output to a temporary file                                                                                                                                                   
    java com.mbahng.lox.Lox "$lox_file" > actual.txt 2>&1                                                                                                                                                 
                                                                                                                                                                                                          
    # 5. Use diff to compare                                                                                                                                                                              
    if diff -u "$expected_output" actual.txt > /dev/null; then                                                                                                                                            
        echo -e "${GREEN}PASS${NC}"                                                                                                                                                                       
    else                                                                                                                                                                                                  
        echo -e "${RED}FAIL${NC}"                                                                                                                                                                         
        # Optional: Show the difference if it fails                                                                                                                                                       
        diff -u "$expected_output" actual.txt                                                                                                                                                             
    fi                                                                                                                                                                                                    
done                                                                                                                                                                                                      
                                                                                                                                                                                                          
# 6. Cleanup                                                                                                                                                                                              
rm actual.txt                                                                                                                                                                                             
