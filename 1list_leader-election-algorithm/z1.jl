using Gadfly
include( "leader_algorithms.jl")

n = 1000
probes = 10000
results = Array{Int64}(probes)

for i = 1:probes
    results[ i ] = leader_firstscenario( n )
end

Gadfly.plot( x = results, Geom.histogram )
