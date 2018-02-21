using Gadfly
include( "leader_algorithms.jl")

u = 1000
n = 1000
probes = 1000
results = Array{Int64}(probes)

# for i = 1:probes
#     results[ i ] = leader_firstscenario( n )
# end
#
# Gadfly.plot( x = results, Geom.histogram )

for i = 1:probes
    results[ i ] = leader_secondscenario( u, n )
    println( i )
end

Gadfly.plot( x = results, Geom.histogram )
