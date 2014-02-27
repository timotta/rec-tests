from random import random
import sys

file_name = sys.argv[1] if len(sys.argv) > 1 else "/tmp/teste.csv"
qtd = int(sys.argv[2]) if len(sys.argv) > 2 else 10000

x = []
for i in xrange(qtd):
    x.append("%d,%d,%d" % (int(random() * 50), int(random() * 500), int((random() * 5))))
    
f = open(file_name, "w")
f.write("\n".join(x))
f.close

print(file_name)
