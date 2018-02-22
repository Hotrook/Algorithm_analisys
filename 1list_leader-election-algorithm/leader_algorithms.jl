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
    m = ceil(Int64, log( 2, u ))
    probabilities = Array{Float64, 1}( m )
    probabilities[ 1 ] = 1/2
    for i = 2 : m
        probabilities[ i ] = probabilities[ i - 1 ] * 0.5
    end

    steps = 0
    transmitted = 0
    while transmitted != 1
        transmitted = 0
        for i = 1:n
            if rand() < probabilities[ ( (steps) % m ) + 1 ]
                transmitted += 1
            end
        end
        steps += 1
    end
    return steps
end

function expected_value( results::Array{Int64, 1} )
    probes = size(results)[ 1 ]

    ev = 0.0
    for i in results
        ev += i/probes
    end

    return ev
end

function variance( results::Array{Int64, 1}, ev::Float64 )
    probes = size(results)[ 1 ]

    var = 0.0
    for i in results
        var += (( i - ev )^2)/probes
    end

    return var
end

function success_in_first_round( results::Array{Int64, 1}, u)
    m = ceil(Int64, log( 2, u ))
    probes = size( results )[ 1 ]
    success = 0

    for i in results
        if i <= m
            success += 1
        end
    end

    return success / probes
end
