using StatsBase
n = 1000
probes = 1000

function leader_firstscenario(n::Integer)
    probability = 1 / n
    leader_found = false
    steps = 0
    transmitted = 0

    while !leader_found
        transmitted = 0
        for i = 1:n
            r = rand()
            if r < probability
                transmitted += 1
            end
        end
        steps += 1
        if transmitted == 1
            leader_found = true
        end
    end

    return steps
end

results = Array{Int64}(probes)

for i = 1:probes
    results[ i ] = leader_firstscenario( 1000 )
end

h = fit( Histrogram, results )

println(h)
