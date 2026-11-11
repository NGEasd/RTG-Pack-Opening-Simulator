type InfoCardProps = {
    titleProp: string;
    descriptProp: string;
};

function InfoCard(info:InfoCardProps) {
    return (
        <div className='info-card'>
            <h3 className='info-card-title'>{info.titleProp}</h3>
            <p className='info-card-description'>{info.descriptProp}</p>
        </div>
    )
}

export default InfoCard;