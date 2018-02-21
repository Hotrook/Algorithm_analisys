function leader_firstscenario(n::Integer)
    probability = 1 / n
    leader_found = false
    steps = 0
    transmitted = 0

    while transmitted != 1
        transmitted = 0
        for i = 1:n
            if rand() < probability
                transmitted += 1
            end
        end
        steps += 1
    end

    return steps
end

function leader_secondscenario(u::Integer, n::Integer)
    d = ceil(Int64, log( u ))
    probabilities = Array{Float64, 1}( d )
    probabilities[ 1 ] = 1/2
    for i = 2 : d
        probabilities[ i ] = probabilities[ i - 1 ] * 0.5
    end

    steps = 0
    transmitted = 0
    while transmitted != 1
        transmitted = 0
        for i = 1:n
            if rand() < probabilities[ ( (steps) % d ) + 1 ]
                transmitted += 1
            end
        end
        steps += 1
    end
    return steps
end
