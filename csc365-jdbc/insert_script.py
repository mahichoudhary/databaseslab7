import csv
import sys

def processArgs(argv):
    if len(argv) < 1:
        print("Missing filename: python3 insert_script.py <filename> <tablename>")
        exit(1)
    fname = argv[0]
    return fname, argv[1:]

def getInserts(fname, args):
    with open(fname, 'r') as csvfd:
        csvreader = csv.reader(csvfd)

        attributes = next(csvreader)

        if len(args) >= 1:
            tablename = args.pop(0)

            # print("row1: {}".format(attributes))
            
            for row in csvreader:
                print("stmt.execute(\"INSERT INTO {} ({}) VALUES ('{}')\");".format(tablename, ', '.join(attributes), "', '".join(row)))
        else:
            print("Missing tablename: python3 insert_script.py <filename> <tablename>")
            exit(1)

def main(argv):

    fname, args = processArgs(argv)

    getInserts(fname, args)

if __name__ == "__main__":
    main(sys.argv[1:])
