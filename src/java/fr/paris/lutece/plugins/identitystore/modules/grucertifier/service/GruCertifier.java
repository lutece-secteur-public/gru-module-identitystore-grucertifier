/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.identitystore.modules.grucertifier.service;

import fr.paris.lutece.plugins.grubusiness.business.customer.Customer;
import fr.paris.lutece.plugins.grubusiness.business.demand.Demand;
import fr.paris.lutece.plugins.grubusiness.business.notification.BackofficeNotification;
import fr.paris.lutece.plugins.grubusiness.business.notification.BroadcastNotification;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.MyDashboardNotification;
import fr.paris.lutece.plugins.grubusiness.business.notification.EmailAddress;
import fr.paris.lutece.plugins.identitystore.business.Identity;
import fr.paris.lutece.plugins.identitystore.business.IdentityAttributeHome;
import fr.paris.lutece.plugins.identitystore.business.IdentityHome;
import fr.paris.lutece.plugins.identitystore.service.certifier.AbstractCertifier;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.librarynotifygru.services.NotificationService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.web.l10n.LocaleService;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Gru Certifier Service extends BaseCertifier for handling of notifications to GRU
 */
public abstract class GruCertifier extends AbstractCertifier
{
    /**
     * @param strCode
     */
    public GruCertifier( String strCode )
    {
        super( strCode );
    }

    private static final String GRU_CERTIFIER_APP_CODE = "GruCertifier";
    private static final String ATTRIBUTE_EMAIL = "email";

    private String _strDemandPrefix;
    private int _nIdCloseDemandStatus;
    private int _nIdCloseCrmStatus;
    private String _strIdDemandType;
    private String _strMessageGruNotifDashboardSubject;
    private String _strMessageGruNotifDashboardMessage;
    private String _strMessageGruNotifDashboardStatusText;
    private String _strMessageGruNotifDashboardSenderName;
    private String _strMessageGruNotifDashboardData;
    private String _strMessageGruNotifEmailMessage;
    private String _strMessageGruNotifEmailSubject;
    private String _strMessageGruNotifSenderMail;
    private String _strMessageGruNotifSenderName;
    private String _strMessageGruNotifAgentMessage;
    private String _strMessageGruNotifAgentStatusText;

    private NotificationService _notifyGruSenderService;

    /**
     * Returns the DemandPrefix
     * 
     * @return The DemandPrefix
     */
    public String getDemandPrefix( )
    {
        return _strDemandPrefix;
    }

    /**
     * Sets the DemandPrefix
     * 
     * @param strDemandPrefix
     *            The DemandPrefix
     */
    public void setDemandPrefix( String strDemandPrefix )
    {
        _strDemandPrefix = strDemandPrefix;
    }

    /**
     * Returns the IdCloseDemandStatus
     * 
     * @return The IdCloseDemandStatus
     */
    public int getIdCloseDemandStatus( )
    {
        return _nIdCloseDemandStatus;
    }

    /**
     * Sets the IdCloseDemandStatus
     * 
     * @param nIdCloseDemandStatus
     *            The IdCloseDemandStatus
     */
    public void setIdCloseDemandStatus( int nIdCloseDemandStatus )
    {
        _nIdCloseDemandStatus = nIdCloseDemandStatus;
    }

    /**
     * Returns the IdCloseCrmStatus
     * 
     * @return The IdCloseCrmStatus
     */
    public int getIdCloseCrmStatus( )
    {
        return _nIdCloseCrmStatus;
    }

    /**
     * Sets the IdCloseCrmStatus
     * 
     * @param nIdCloseCrmStatus
     *            The IdCloseCrmStatus
     */
    public void setIdCloseCrmStatus( int nIdCloseCrmStatus )
    {
        _nIdCloseCrmStatus = nIdCloseCrmStatus;
    }

    /**
     * Returns the IdDemandType
     * 
     * @return The IdDemandType
     */
    public String getIdDemandType( )
    {
        return _strIdDemandType;
    }

    /**
     * Sets the IdDemandType
     * 
     * @param strIdDemandType
     *            The IdDemandType
     */
    public void setIdDemandType( String strIdDemandType )
    {
        _strIdDemandType = strIdDemandType;
    }

    /**
     * Returns the MessageGruNotifDashboardSubject
     * 
     * @return The MessageGruNotifDashboardSubject
     */
    public String getMessageGruNotifDashboardSubject( )
    {
        return _strMessageGruNotifDashboardSubject;
    }

    /**
     * Sets the MessageGruNotifDashboardSubject
     * 
     * @param strMessageGruNotifDashboardSubject
     *            The MessageGruNotifDashboardSubject
     */
    public void setMessageGruNotifDashboardSubject( String strMessageGruNotifDashboardSubject )
    {
        _strMessageGruNotifDashboardSubject = strMessageGruNotifDashboardSubject;
    }

    /**
     * Returns the MessageGruNotifDashboardMessage
     * 
     * @return The MessageGruNotifDashboardMessage
     */
    public String getMessageGruNotifDashboardMessage( )
    {
        return _strMessageGruNotifDashboardMessage;
    }

    /**
     * Sets the MessageGruNotifDashboardMessage
     * 
     * @param strMessageGruNotifDashboardMessage
     *            The MessageGruNotifDashboardMessage
     */
    public void setMessageGruNotifDashboardMessage( String strMessageGruNotifDashboardMessage )
    {
        _strMessageGruNotifDashboardMessage = strMessageGruNotifDashboardMessage;
    }

    /**
     * Returns the MessageGruNotifDashboardStatusText
     * 
     * @return The MessageGruNotifDashboardStatusText
     */
    public String getMessageGruNotifDashboardStatusText( )
    {
        return _strMessageGruNotifDashboardStatusText;
    }

    /**
     * Sets the MessageGruNotifDashboardStatusText
     * 
     * @param strMessageGruNotifDashboardStatusText
     *            The MessageGruNotifDashboardStatusText
     */
    public void setMessageGruNotifDashboardStatusText( String strMessageGruNotifDashboardStatusText )
    {
        _strMessageGruNotifDashboardStatusText = strMessageGruNotifDashboardStatusText;
    }

    /**
     * Returns the MessageGruNotifDashboardSenderName
     * 
     * @return The MessageGruNotifDashboardSenderName
     */
    public String getMessageGruNotifDashboardSenderName( )
    {
        return _strMessageGruNotifDashboardSenderName;
    }

    /**
     * Sets the MessageGruNotifDashboardSenderName
     * 
     * @param strMessageGruNotifDashboardSenderName
     *            The MessageGruNotifDashboardSenderName
     */
    public void setMessageGruNotifDashboardSenderName( String strMessageGruNotifDashboardSenderName )
    {
        _strMessageGruNotifDashboardSenderName = strMessageGruNotifDashboardSenderName;
    }

    /**
     * Returns the MessageGruNotifDashboardData
     * 
     * @return The MessageGruNotifDashboardData
     */
    public String getMessageGruNotifDashboardData( )
    {
        return _strMessageGruNotifDashboardData;
    }

    /**
     * Sets the MessageGruNotifDashboardData
     * 
     * @param strMessageGruNotifDashboardData
     *            The MessageGruNotifDashboardData
     */
    public void setMessageGruNotifDashboardData( String strMessageGruNotifDashboardData )
    {
        _strMessageGruNotifDashboardData = strMessageGruNotifDashboardData;
    }

    /**
     * Returns the MessageGruNotifEmailMessage
     * 
     * @return The MessageGruNotifEmailMessage
     */
    public String getMessageGruNotifEmailMessage( )
    {
        return _strMessageGruNotifEmailMessage;
    }

    /**
     * Sets the MessageGruNotifEmailMessage
     * 
     * @param strMessageGruNotifEmailMessage
     *            The MessageGruNotifEmailMessage
     */
    public void setMessageGruNotifEmailMessage( String strMessageGruNotifEmailMessage )
    {
        _strMessageGruNotifEmailMessage = strMessageGruNotifEmailMessage;
    }

    /**
     * Returns the MessageGruNotifEmailSubject
     * 
     * @return The MessageGruNotifEmailSubject
     */
    public String getMessageGruNotifEmailSubject( )
    {
        return _strMessageGruNotifEmailSubject;
    }

    /**
     * Sets the MessageGruNotifEmailSubject
     * 
     * @param strMessageGruNotifEmailSubject
     *            The MessageGruNotifEmailSubject
     */
    public void setMessageGruNotifEmailSubject( String strMessageGruNotifEmailSubject )
    {
        _strMessageGruNotifEmailSubject = strMessageGruNotifEmailSubject;
    }

    /**
     * Returns the MessageGruNotifSenderMail
     * 
     * @return The MessageGruNotifSenderMail
     */
    public String getMessageGruNotifSenderMail( )
    {
        return _strMessageGruNotifSenderMail;
    }

    /**
     * Sets the MessageGruNotifSenderMail
     * 
     * @param strMessageGruNotifSenderMail
     *            The MessageGruNotifSenderMail
     */
    public void setMessageGruNotifSenderMail( String strMessageGruNotifSenderMail )
    {
        _strMessageGruNotifSenderMail = strMessageGruNotifSenderMail;
    }

    /**
     * Returns the MessageGruNotifSenderName
     * 
     * @return The MessageGruNotifSenderName
     */
    public String getMessageGruNotifSenderName( )
    {
        return _strMessageGruNotifSenderName;
    }

    /**
     * Sets the MessageGruNotifSenderName
     * 
     * @param strMessageGruNotifSenderName
     *            The MessageGruNotifSenderName
     */
    public void setMessageGruNotifSenderName( String strMessageGruNotifSenderName )
    {
        _strMessageGruNotifSenderName = strMessageGruNotifSenderName;
    }

    /**
     * Returns the MessageGruNotifAgentMessage
     * 
     * @return The MessageGruNotifAgentMessage
     */
    public String getMessageGruNotifAgentMessage( )
    {
        return _strMessageGruNotifAgentMessage;
    }

    /**
     * Sets the MessageGruNotifAgentMessage
     * 
     * @param strMessageGruNotifAgentMessage
     *            The MessageGruNotifAgentMessage
     */
    public void setMessageGruNotifAgentMessage( String strMessageGruNotifAgentMessage )
    {
        _strMessageGruNotifAgentMessage = strMessageGruNotifAgentMessage;
    }

    /**
     * Returns the MessageGruNotifAgentStatusText
     * 
     * @return The MessageGruNotifAgentStatusText
     */
    public String getMessageGruNotifAgentStatusText( )
    {
        return _strMessageGruNotifAgentStatusText;
    }

    /**
     * Sets the MessageGruNotifAgentStatusText
     * 
     * @param strMessageGruNotifAgentStatusText
     *            The MessageGruNotifAgentStatusText
     */
    public void setMessageGruNotifAgentStatusText( String strMessageGruNotifAgentStatusText )
    {
        _strMessageGruNotifAgentStatusText = strMessageGruNotifAgentStatusText;
    }

    /**
     * Setter for Spring Context
     * 
     * @param service
     *            The notification
     */
    public void setNotificationService( NotificationService service )
    {
        _notifyGruSenderService = service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void beforeCertify( IdentityDto identityDto, String strClientAppCode )
    {
        // nothing todo
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void afterCertify( IdentityDto identityDto, String strClientAppCode, List<String> listCertifiedAttribut )
    {
        // Test the existence of a transport provider
        if ( listCertifiedAttribut != null && !listCertifiedAttribut.isEmpty( ) )
        {
            if ( _notifyGruSenderService != null )
            {
                Notification certifNotif = buildCertifiedNotif( identityDto, LocaleService.getDefault( ) );

                _notifyGruSenderService.send( certifNotif );
            }
            else
            {
                // mock mode => certification message is logged
                AppLogService.info( _strMessageGruNotifDashboardSubject );
            }
        }
        else
        {
            AppLogService.info( "No attribut have been certified" );
        }
    }

    /**
     * build a notification from validation infos
     *
     * @param identityDto
     *            identity data
     * @param locale
     *            locale
     * @return Notification notification to send (SMS, agent, dashboard, email)
     */
    private Notification buildCertifiedNotif( IdentityDto identityDto, Locale locale )
    {
        initNotifyGruConfig( locale );

        Notification certifNotif = new Notification( );
        certifNotif.setDate( new Date( ).getTime( ) );

        // set de Demand
        Demand demand = new Demand( );
        demand.setId( generateDemandId( identityDto.getConnectionId( ) ) );
        demand.setReference( _strDemandPrefix + demand.getId( ) );
        demand.setStatusId( _nIdCloseDemandStatus );
        demand.setTypeId( _strIdDemandType );

        Customer customer = new Customer( );
        customer.setConnectionId( identityDto.getConnectionId( ) );
        Identity identity = IdentityHome.findByConnectionId( identityDto.getConnectionId( ), GRU_CERTIFIER_APP_CODE );
        if ( identity != null && MapUtils.isNotEmpty( identity.getAttributes( ) ) && identity.getAttributes( ).containsKey( ATTRIBUTE_EMAIL ) )
        {
            String strEmail = IdentityHome.findByConnectionId( identityDto.getConnectionId( ), GRU_CERTIFIER_APP_CODE ).getAttributes( ).get( ATTRIBUTE_EMAIL )
                    .getValue( );
            customer.setEmail( strEmail );
        }
        demand.setCustomer( customer );

        certifNotif.setDemand( demand );

        // set the Dashboard Notif
        MyDashboardNotification notifDashboard = new MyDashboardNotification( );
        notifDashboard.setStatusId( _nIdCloseCrmStatus );
        notifDashboard.setSubject( _strMessageGruNotifDashboardSubject );
        notifDashboard.setMessage( _strMessageGruNotifDashboardMessage );
        notifDashboard.setStatusText( _strMessageGruNotifDashboardStatusText );
        notifDashboard.setSenderName( _strMessageGruNotifDashboardSenderName );
        notifDashboard.setData( _strMessageGruNotifDashboardData );
        certifNotif.setMyDashboardNotification( notifDashboard );

        // set the BroadCast Email
        if ( StringUtils.isNotEmpty( customer.getEmail( ) ) )
        {
            BroadcastNotification broadcastEmail = new BroadcastNotification( );
            broadcastEmail.setMessage( _strMessageGruNotifEmailMessage );
            broadcastEmail.setSubject( _strMessageGruNotifEmailSubject );
            broadcastEmail.setSenderEmail( _strMessageGruNotifSenderMail );
            broadcastEmail.setSenderName( _strMessageGruNotifSenderName );

            broadcastEmail.setRecipient( EmailAddress.buildEmailAddresses( new String [ ] {
                customer.getEmail( )
            } ) );
            certifNotif.addBroadcastEmail( broadcastEmail );
        }

        // set the Backoffice Notif
        BackofficeNotification notifAgent = new BackofficeNotification( );
        notifAgent.setMessage( _strMessageGruNotifAgentMessage );
        notifAgent.setStatusText( _strMessageGruNotifAgentStatusText );
        certifNotif.setBackofficeNotification( notifAgent );

        return certifNotif;
    }

    /**
     * generate demandid for sms certification
     *
     * @return demand id
     */
    private String generateDemandId( String strConnectionId )
    {
        return String.valueOf( IdentityAttributeHome.getLastIdHistory( strConnectionId, getCode( ) ) );
    }

    /**
     * Init the messages for gru Notification
     * 
     * @param locale
     *            The locale
     */
    protected abstract void initNotifyGruConfig( Locale locale );

}
